package com.example.shows.network.response.show

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val average: Double?
)