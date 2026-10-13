package com.example.test_sdk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CountrySearchResponse(
    val query: String? = null,
    val countries: List<CountryDto> = emptyList(),
)

@Serializable
data class CountryDto(
    val name: String,
    val officialName: String? = null,
    val cca2: String? = null,
    val cca3: String? = null,
    val capital: List<String> = emptyList(),
    val region: String? = null,
    val subregion: String? = null,
    val flagEmoji: String? = null,
    val flagUrl: String? = null,
    val latLng: List<Double> = emptyList(),
)

data class CountryLocation(
    val name: String,
    val officialName: String?,
    val capital: String?,
    val latitude: Double,
    val longitude: Double,
    val flagEmoji: String?,
)
