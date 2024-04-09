package com.example.shows.network.response.searchresult

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String?,
    val name: String?,
    val timezone: String?
)