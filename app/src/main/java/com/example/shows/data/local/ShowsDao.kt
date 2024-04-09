package com.example.shows.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowsDao {

    @Query("SELECT * FROM show ORDER BY name")
    fun getShows(): Flow<List<Show>>

    @Delete
    suspend fun deleteShow(show: Show)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveShow(show: Show)
    @Query("SELECT * FROM show WHERE show_id=:id")
    fun getShow(id: Int): Show
}