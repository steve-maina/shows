package com.example.shows.network.response.searchresult
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val medium: String?,
    val original: String?
)