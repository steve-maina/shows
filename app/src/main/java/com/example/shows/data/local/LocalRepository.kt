package com.example.shows.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface LocalRepository {
    suspend fun saveToDb(show: Show)
    fun getShows(): Flow<List<Show>>
    suspend fun deleteShow(show: Show)
    suspend fun getShow(showId: Int): Show
}



class FavoriteLocalRepository (val showsDao: ShowsDao): LocalRepository{
    override suspend fun saveToDb(show: Show) {
        showsDao.saveShow(show)
    }

    override fun getShows(): Flow<List<Show>> {
        return showsDao.getShows()
    }

    override suspend fun deleteShow(show: Show) {
        return showsDao.deleteShow(show)
    }

    override suspend fun getShow(showId: Int): Show {
        return showsDao.getShow(showId)
    }
}