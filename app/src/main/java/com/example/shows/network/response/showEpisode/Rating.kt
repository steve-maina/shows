package com.example.shows.network.response.showEpisode

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val average: Double? = null
)