package com.example.shows.network.response.show

import com.example.shows.network.response.searchresult.Nextepisode
import com.example.shows.network.response.searchresult.Previousepisode
import com.example.shows.network.response.searchresult.Self
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val nextepisode: Nextepisode = Nextepisode(""),
    val previousepisode: Previousepisode = Previousepisode(""),
    val self: Self?
)