package com.example.test_sdk.example

import com.aivo.sdk.AivoProvider
import com.aivo.sdk.AivoSdk
import com.aivo.sdk.core.model.ConversationId
import com.aivo.sdk.core.port.Tool
import com.aivo.sdk.runtime.agent.AgentDefinition
import com.aivo.sdk.runtime.agent.AgentDefinitionBuilder
import com.aivo.sdk.runtime.agent.AgentEvent
import com.aivo.sdk.runtime.tool.ParamType
import com.aivo.sdk.runtime.tool.tool
import com.example.test_sdk.data.network.WeatherRemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * End-to-end working example demonstrating:
 * 1. Tool 1: get_country_coordinates (Country Name -> Lat, Lon, Capital, Flag)
 * 2. Tool 2: get_weather_conditions (Lat, Lon -> Temperature, Conditions, Wind, Rain)
 * 3. Weather Assistant Agent definition managing both tools.
 * 4. Streaming execution with [AivoSdk.stream].
 *
 * Placed inside a registered Gradle source set (`shared/src/commonMain/kotlin`),
 * ensuring full compile-time access to [AgentDefinition] from `sdk-runtime`.
 */
class WeatherAgentExample(
    private val geminiApiKey: String,
    private val service: WeatherRemoteService = WeatherRemoteService(),
) {

    // Tool 1: Lookup latitude and longitude by country name
    val countryCoordinatesTool: Tool = tool(
        name = "get_country_coordinates",
        description = "Get the latitude, longitude, capital, and flag emoji for any country by its name",
    ) {
        param(
            name = "country",
            description = "Name of the country (e.g. Egypt, France, Japan, Germany)",
            type = ParamType.String,
            required = true,
        )
        execute { args ->
            val countryName = args.string("country")
            val location = service.getCountryCoordinates(countryName)
            buildJsonObject {
                put("country", location.name)
                location.officialName?.let { put("officialName", it) }
                location.capital?.let { put("capital", it) }
                put("latitude", location.latitude)
                put("longitude", location.longitude)
                location.flagEmoji?.let { put("flagEmoji", it) }
            }
        }
    }

    // Tool 2: Lookup current weather conditions by latitude and longitude
    val weatherConditionsTool: Tool = tool(
        name = "get_weather_conditions",
        description = "Get current weather conditions (temperature, condition, wind, precipitation) for given latitude and longitude coordinates",
    ) {
        param(
            name = "lat",
            description = "Latitude of the target location (e.g. 27.0)",
            type = ParamType.Double,
            required = true,
        )
        param(
            name = "lon",
            description = "Longitude of the target location (e.g. 30.0)",
            type = ParamType.Double,
            required = true,
        )
        execute { args ->
            val lat = args.doubleOrNull("lat")
                ?: args.stringOrNull("lat")?.toDoubleOrNull()
                ?: throw IllegalArgumentException("Missing coordinate 'lat'")

            val lon = args.doubleOrNull("lon")
                ?: args.stringOrNull("lon")?.toDoubleOrNull()
                ?: throw IllegalArgumentException("Missing coordinate 'lon'")

            val report = service.getWeatherConditions(lat, lon)
            buildJsonObject {
                put("latitude", report.latitude)
                put("longitude", report.longitude)
                report.timezone?.let { put("timezone", it) }
                put("temperatureC", report.temperatureC)
                report.apparentTemperatureC?.let { put("apparentTemperatureC", it) }
                put("condition", report.condition)
                report.windSpeedKmh?.let { put("windSpeedKmh", it) }
                report.precipitationMm?.let { put("precipitationMm", it) }
                report.isDay?.let { put("isDay", it) }
            }
        }
    }

    /**
     * Builds and configures the Aivo SDK instance.
     * Note: [define] returns an [AgentDefinition], which is fully resolved
     * via the direct `sdk-runtime` dependency in `shared/build.gradle.kts`.
     */
    fun createSdk(): AivoSdk {
        return AivoSdk {
            providers {
                gemini("gemini") {
                    apiKey(geminiApiKey)
                }
            }

            defaultModel("gemini:gemini-2.0-flash")

            tools {
                register(countryCoordinatesTool)
                register(weatherConditionsTool)
            }

            agents {
                define("weather_assistant") {
                    name = "Weather Assistant"
                    systemPrompt = """
                        You are a specialized Weather Assistant for global countries.
                        When asked about the weather or conditions for a country:
                        1. Always first call the 'get_country_coordinates' tool to retrieve the country's latitude, longitude, capital, and flag.
                        2. Next, call the 'get_weather_conditions' tool using the extracted latitude and longitude to get the current temperature, wind speed, precipitation, and sky conditions.
                        3. Finally, formulate a friendly, concise, and beautifully structured response containing:
                           - Country Name, Official Name & Flag Emoji
                           - Capital city & Coordinates (Latitude, Longitude)
                           - Current Weather Condition & Temperature (in Celsius and apparent)
                           - Wind Speed & Precipitation
                    """.trimIndent()
                    tools(countryCoordinatesTool, weatherConditionsTool)
                }
            }

            entryAgent("weather_assistant")

            runtime {
                maxSteps = 8
            }
        }
    }

    /**
     * Executes a user prompt through the agent loop and returns a stream of events.
     */
    fun askWeather(countryQuery: String): Flow<AgentEvent> {
        val sdk = createSdk()
        return sdk.stream(
            conversationId = ConversationId("weather-session"),
            message = "What is the current weather in $countryQuery?",
        )
    }
}
