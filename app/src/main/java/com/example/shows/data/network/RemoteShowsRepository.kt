package com.example.shows.data.network



import com.example.shows.data.network.response.SearchShow
import com.example.shows.data.network.response.ShowEpisode
import com.example.shows.data.network.response.ShowResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path


interface ShowsRepository {
    suspend fun searchShows(showName: String): List<ShowResult>
    suspend fun getEpisodeInfo(episode: String): ShowEpisode

    suspend fun getShow(showId: Int): SearchShow
}


class RemoteShowsRepository (val client: HttpClient): ShowsRepository {
    override suspend  fun searchShows(showName: String): List<ShowResult> {
//        return retrofitService.searchShows(showName)
        return client.get{
            url {
                path("search/shows")
                parameters.append("q",showName)
            }
        }.body()
    }

    override suspend fun getEpisodeInfo(episode: String): ShowEpisode {
        return client.get {
            url {
                path("episodes/${episode}")
            }
        }.body()
    }

    override suspend fun getShow(showId: Int): SearchShow {
        return client.get {
            url {
                path("shows/${showId}")
            }
        }.body()
    }
}