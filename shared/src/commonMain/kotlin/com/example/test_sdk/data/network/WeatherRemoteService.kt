package com.example.test_sdk.data.network

import com.example.test_sdk.data.model.CountryLocation
import com.example.test_sdk.data.model.CountrySearchResponse
import com.example.test_sdk.data.model.WeatherCurrentResponse
import com.example.test_sdk.data.model.WeatherReport
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Service using Ktor to interact with FreeOpenAPI Geography and Weather endpoints.
 */
class WeatherRemoteService(
    private val client: HttpClient = createDefaultHttpClient(),
    private val apiKey: String = DEFAULT_API_KEY,
) {

    companion object {
        const val DEFAULT_API_KEY = "foa_BW00Vz303vN5Fx9mIR3MrE4VZppHHvDS"
        private const val API_KEY_HEADER = "X-Api-Key"
        private const val COUNTRIES_URL = "https://freeopenapi.dev/api/v1/geography/countries"
        private const val WEATHER_URL = "https://freeopenapi.dev/api/v1/weather/current"

        fun createDefaultHttpClient(): HttpClient = HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = false
                    }
                )
            }
        }
    }

    /**
     * Tool 1: Get the latitude and longitude for any country by name.
     * Endpoint: https://freeopenapi.dev/api/v1/geography/countries?search={name}
     */
    suspend fun getCountryCoordinates(countryName: String): CountryLocation {
        val cleanName = countryName.trim().removeSurrounding("\"")

        // First attempt with clean query
        var response = runCatching {
            client.get(COUNTRIES_URL) {
                header(API_KEY_HEADER, apiKey)
                parameter("search", cleanName)
            }.body<CountrySearchResponse>()
        }.getOrNull()

        // Fallback attempt with quoted query if first returned empty countries
        if (response == null || response.countries.isEmpty()) {
            val fallbackResponse = runCatching {
                client.get(COUNTRIES_URL) {
                    header(API_KEY_HEADER, apiKey)
                    parameter("search", "\"$cleanName\"")
                }.body<CountrySearchResponse>()
            }.getOrNull()

            if (fallbackResponse != null && fallbackResponse.countries.isNotEmpty()) {
                response = fallbackResponse
            }
        }

        val country = response?.countries?.firstOrNull()
            ?: throw NoSuchElementException("Country '$countryName' not found.")

        if (country.latLng.size < 2) {
            throw IllegalStateException("Country '${country.name}' returned invalid coordinates: ${country.latLng}")
        }

        return CountryLocation(
            name = country.name,
            officialName = country.officialName,
            capital = country.capital.firstOrNull(),
            latitude = country.latLng[0],
            longitude = country.latLng[1],
            flagEmoji = country.flagEmoji,
        )
    }

    /**
     * Tool 2: Get current weather conditions for given coordinates (lat, lon).
     * Endpoint: https://freeopenapi.dev/api/v1/weather/current?lat={lat}&lon={lon}
     */
    suspend fun getWeatherConditions(lat: Double, lon: Double): WeatherReport {
        val response = client.get(WEATHER_URL) {
            header(API_KEY_HEADER, apiKey)
            parameter("lat", lat)
            parameter("lon", lon)
        }.body<WeatherCurrentResponse>()

        val current = response.current
            ?: throw IllegalStateException("No current weather data available for coordinates ($lat, $lon).")

        return WeatherReport(
            latitude = response.latitude ?: lat,
            longitude = response.longitude ?: lon,
            timezone = response.timezone,
            temperatureC = current.temperatureC ?: 0.0,
            apparentTemperatureC = current.apparentTemperatureC,
            condition = current.condition ?: "Unknown",
            windSpeedKmh = current.windSpeedKmh,
            precipitationMm = current.precipitationMm,
            isDay = current.isDay,
        )
    }
}
