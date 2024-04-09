package com.example.shows.network.response.show

import com.example.shows.network.response.searchresult.Externals
import com.example.shows.network.response.searchresult.Image
import com.example.shows.network.response.searchresult.Links
import com.example.shows.network.response.searchresult.Network
import com.example.shows.network.response.searchresult.Rating
import com.example.shows.network.response.searchresult.Schedule
import com.example.shows.network.response.searchresult.WebChannel
import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val _links: Links? = Links(self=null),
    val averageRuntime: Int?,
    val dvdCountry: String?,
    val ended: String?,
    val externals: Externals? = Externals(null,null,null),
    val genres: List<String>?,
    val id: Int?,
    val image: Image? = Image(null,null),
    val language: String?,
    val name: String?,
    val network: Network? = Network(null,null,null,null),
    val officialSite: String?,
    val premiered: String?,
    val rating: Rating? = Rating(null),
    val runtime: Int?,
    val schedule: Schedule? =  Schedule(listOf<String>(),""),
    val status: String?,
    val summary: String?,
    val type: String?,
    val updated: Int?,
    val url: String?,
    val webChannel: WebChannel?,
    val weight: Int?
)