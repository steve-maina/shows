package com.example.shows.network.response.show

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val days: List<String>?,
    val time: String?
)