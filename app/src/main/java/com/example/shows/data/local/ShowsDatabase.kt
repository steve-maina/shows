package com.example.shows.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Show::class], version = 2, exportSchema = false)
abstract class ShowsDatabase: RoomDatabase() {

    abstract fun getDao(): ShowsDao
    companion object {
        @Volatile
        private var Instance: ShowsDatabase? = null

        fun getDatabase(context: Context): ShowsDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,ShowsDatabase::class.java,"favorites")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}