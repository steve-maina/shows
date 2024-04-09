package com.example.shows.network.response.searchresult

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val country: Country?,
    val id: Int?,
    val name: String?,
    val officialSite: String?
)