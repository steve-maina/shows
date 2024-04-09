package com.example.shows.network.response.searchresult

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val nextepisode: Nextepisode = Nextepisode(""),
    val previousepisode: Previousepisode = Previousepisode(""),
    val self: Self?
)