package com.example.shows.network.response.searchresult
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultItem(
    val score: Double,
    val show: Show
)