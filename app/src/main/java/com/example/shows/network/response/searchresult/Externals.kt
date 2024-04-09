package com.example.shows.network.response.searchresult
import kotlinx.serialization.Serializable

@Serializable
data class Externals(
    val imdb: String?,
    val thetvdb: Int?,
    val tvrage: Int?
)