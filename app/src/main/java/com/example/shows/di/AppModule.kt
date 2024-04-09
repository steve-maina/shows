package com.example.shows.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.shows.data.RemoteShowsRepository
import com.example.shows.data.ShowsRepository
import com.example.shows.data.local.FavoriteLocalRepository
import com.example.shows.data.local.LocalRepository
import com.example.shows.data.local.ShowsDao
import com.example.shows.data.local.ShowsDatabase

import com.example.shows.network.ShowsApi
import com.example.shows.network.ShowsApi.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton
const private val preferencesName = "dataStore1"

val Context.dataStore by preferencesDataStore(
    name = preferencesName
)


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): ShowsApi {
        val json = Json { ignoreUnknownKeys = true } // Set ignoreUnknownKeys to true
        val ConverterFactory = json.asConverterFactory("application/json".toMediaType())
        return Retrofit.Builder()
            .addConverterFactory(ConverterFactory)
            .baseUrl(BASE_URL)
            .build()
            .create(ShowsApi::class.java)

    }

    @Singleton
    @Provides
    fun provideLocalDatabase(app: Application): ShowsDatabase{
        return Room.databaseBuilder(app,ShowsDatabase::class.java,"favorites")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDataStore(app: Application): DataStore<Preferences>{
        return app.dataStore
    }

    @Singleton
    @Provides
    fun provideShowsRepository(api: ShowsApi): ShowsRepository {
        return RemoteShowsRepository(api)
    }

    @Singleton
    @Provides
    fun providesRoomRepository(db: ShowsDatabase): LocalRepository {
        return FavoriteLocalRepository(db.getDao())
    }



}