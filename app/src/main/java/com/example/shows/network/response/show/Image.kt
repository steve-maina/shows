package com.example.shows.network.response.show

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val medium: String?,
    val original: String?
)