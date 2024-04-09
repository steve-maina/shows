package com.example.shows.network.response.searchresult
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val average: Double?
)