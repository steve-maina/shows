package com.example.shows.network.response.show

import com.example.shows.network.response.searchresult.Country
import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val country: Country?,
    val id: Int?,
    val name: String?,
    val officialSite: String?
)