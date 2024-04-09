package com.example.shows.network.response.show

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String?,
    val name: String?,
    val timezone: String?
)