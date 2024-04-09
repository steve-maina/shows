package com.example.shows.network.response.showEpisode

import kotlinx.serialization.Serializable

@Serializable
data class ShowEpisode(
    val _links: Links = Links(self = null),
    val airdate: String? = "",
    val airstamp: String? = "",
    val airtime: String? = "",
    val id: Int? = null,
    val image: Image? = Image("",""),
    val name: String? = null,
    val number: Int? = null,
    val rating: Rating? = null,
    val runtime: Int? = null,
    val season: Int? = null,
    val summary: String? = null,
    val type: String? = null,
    val url: String? =  null
)