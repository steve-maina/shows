package com.example.shows.network

import com.example.shows.network.response.searchresult.SearchResultItem
import com.example.shows.network.response.show.Show
import com.example.shows.network.response.showEpisode.ShowEpisode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowsApi {
    @GET("search/shows")
    suspend fun searchShows(@Query("q")showName: String): List<SearchResultItem>

    @GET("/episodes/{id}")
    suspend fun getEpisode(@Path("id") episode: String):ShowEpisode

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): Show

    companion object {

        const val BASE_URL = "https://api.tvmaze.com"
    }

}