package com.example.test_sdk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCurrentResponse(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val timezone: String? = null,
    val current: CurrentWeatherDto? = null,
)

@Serializable
data class CurrentWeatherDto(
    val timestamp: String? = null,
    val temperatureC: Double? = null,
    val apparentTemperatureC: Double? = null,
    val windSpeedKmh: Double? = null,
    val windDirectionDeg: Double? = null,
    val precipitationMm: Double? = null,
    val weatherCode: Int? = null,
    val condition: String? = null,
    val isDay: Boolean? = null,
)

data class WeatherReport(
    val latitude: Double,
    val longitude: Double,
    val timezone: String?,
    val temperatureC: Double,
    val apparentTemperatureC: Double?,
    val condition: String,
    val windSpeedKmh: Double?,
    val precipitationMm: Double?,
    val isDay: Boolean?,
)
