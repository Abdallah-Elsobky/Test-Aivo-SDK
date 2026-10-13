package com.example.test_sdk.example

/**
 * =========================================================================================
 * ℹ️ Classpath & Import Architecture Note:
 * =========================================================================================
 * If you ever see in the IDE / compiler:
 *   "Cannot access class 'com.aivo.sdk.runtime.agent.AgentDefinition'. Check your module classpath..."
 *
 * This occurs because:
 * 1. `define(id) { ... }` returns an `AgentDefinition` which is declared in module `:sdk-runtime`.
 * 2. In Gradle Kotlin Multiplatform, consumer modules must have compile-time access to the return types.
 *    In `gradle/libs.versions.toml`:
 *      aivo-sdk = { module = "io.github.abdallah-elsobky:aivo-sdk", version.ref = "aivo" }
 *      aivo-sdk-runtime = { module = "io.github.abdallah-elsobky:sdk-runtime", version.ref = "aivo" }
 *      aivo-sdk-core = { module = "io.github.abdallah-elsobky:sdk-core", version.ref = "aivo" }
 *    In `shared/build.gradle.kts` (commonMain.dependencies):
 *      implementation(libs.aivo.sdk)
 *      implementation(libs.aivo.sdk.runtime)
 *      implementation(libs.aivo.sdk.core)
 *
 * 3. In addition, standalone / loose files outside of registered Gradle source sets (e.g. files in `.plan/`)
 *    do not receive the module classpath by default in Android Studio / IntelliJ IDEA.
 *    For production code, keep your source files inside `src/commonMain/kotlin/...` (e.g.,
 *    `shared/src/commonMain/kotlin/com/example/test_sdk/example/WeatherAgentExample.kt`).
 * =========================================================================================
 */

import com.aivo.sdk.AivoProvider
import com.aivo.sdk.AivoSdk
import com.aivo.sdk.core.model.ConversationId
import com.aivo.sdk.core.model.ParamType
import com.aivo.sdk.core.port.Tool
import com.aivo.sdk.runtime.agent.AgentDefinition
import com.aivo.sdk.runtime.agent.AgentDefinitionBuilder
import com.aivo.sdk.runtime.agent.AgentEvent
import com.aivo.sdk.runtime.agent.AgentRegistryBuilder
import com.aivo.sdk.runtime.tool.ToolDefinition
import com.aivo.sdk.runtime.tool.tool
import com.example.test_sdk.data.network.WeatherRemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

// --- Tool 1: get_country_coordinates ---
fun createCountryCoordinatesTool(service: WeatherRemoteService): Tool =
    tool("get_country_coordinates", "Get the latitude, longitude, capital, and flag emoji for any country by its name") {
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

// --- Tool 2: get_weather_conditions ---
fun createWeatherConditionsTool(service: WeatherRemoteService): Tool =
    tool("get_weather_conditions", "Get current weather conditions (temperature, condition, wind, precipitation) for given latitude and longitude coordinates") {
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

// --- Weather Agent Builder ---
fun buildWeatherAgent(
    geminiApiKey: String,
    service: WeatherRemoteService = WeatherRemoteService(),
): AivoSdk {
    val coordsTool = createCountryCoordinatesTool(service)
    val weatherTool = createWeatherConditionsTool(service)

    return AivoSdk {
        providers {
            gemini("gemini") {
                apiKey(geminiApiKey)
            }
        }

        defaultModel("gemini:gemini-2.0-flash")

        tools {
            register(coordsTool)
            register(weatherTool)
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
                tools(coordsTool.name, weatherTool.name)
            }
        }

        entryAgent("weather_assistant")

        runtime {
            maxSteps = 8
        }
    }
}
