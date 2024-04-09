package com.example.shows.data

import com.example.shows.data.local.ShowsDao
import com.example.shows.network.ShowsApi
import com.example.shows.network.response.searchresult.SearchResultItem
import com.example.shows.network.response.show.Show
import com.example.shows.network.response.showEpisode.ShowEpisode
import javax.inject.Inject
import javax.inject.Singleton

interface ShowsRepository {
    suspend fun searchShows(showName: String): List<SearchResultItem>
    suspend fun getEpisodeInfo(episode: String): ShowEpisode
    suspend fun getShow(id: Int): Show
}


class RemoteShowsRepository (val retrofitService: ShowsApi): ShowsRepository{
    override suspend  fun searchShows(showName: String): List<SearchResultItem> {
        return retrofitService.searchShows(showName)
    }

    override suspend fun getEpisodeInfo(episode: String): ShowEpisode {
        return retrofitService.getEpisode(episode)
    }

    override suspend fun getShow(id: Int): Show {
        return retrofitService.getShow(id)
    }
}