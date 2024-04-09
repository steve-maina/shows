package com.example.shows.network.response.showEpisode

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val self: Self? = null,
    val show: Show? = null
)