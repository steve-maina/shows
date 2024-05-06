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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
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
    fun providesKtorClient(): HttpClient{
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json{
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url("https://api.tvmaze.com/")
            }
        }
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
    fun provideShowsRepository( client: HttpClient): ShowsRepository {
        return RemoteShowsRepository(client)
    }

    @Singleton
    @Provides
    fun providesRoomRepository(db: ShowsDatabase): LocalRepository {
        return FavoriteLocalRepository(db.getDao())
    }



}