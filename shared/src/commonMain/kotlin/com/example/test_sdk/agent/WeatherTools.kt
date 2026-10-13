package com.example.test_sdk.agent

import com.aivo.sdk.core.port.Tool
import com.aivo.sdk.runtime.tool.ParamType
import com.aivo.sdk.runtime.tool.tool
import com.example.test_sdk.data.network.WeatherRemoteService
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

object WeatherTools {

    /**
     * Tool 1: Retrieves latitude and longitude for a given country name.
     */
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

    /**
     * Tool 2: Retrieves current weather conditions using latitude and longitude.
     */
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
                    ?: throw IllegalArgumentException("Missing or invalid latitude coordinate 'lat'")

                val lon = args.doubleOrNull("lon")
                    ?: args.stringOrNull("lon")?.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Missing or invalid longitude coordinate 'lon'")

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
}
