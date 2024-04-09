package com.example.shows.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows.data.ShowsRepository
import com.example.shows.data.local.LocalRepository
import com.example.shows.data.local.Show
import com.example.shows.data.local.UserPreferencesRepository
import com.example.shows.ui.screens.HomeUiState
import com.example.shows.ui.screens.components.removeHtml
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val showsRepository: ShowsRepository, val localRepository: LocalRepository, val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    val isDarkTheme = userPreferencesRepository.isDarkTheme.stateIn(scope=viewModelScope,started = SharingStarted.WhileSubscribed(5_000L),initialValue = true)
    var homeUiState by mutableStateOf(HomeUiState())
    var removeShow by mutableStateOf<Show?>(null)
    val favorites = localRepository.getShows().stateIn( scope = viewModelScope,started = SharingStarted.WhileSubscribed(5000L), initialValue = listOf<Show>(),)
    var searchShowsJob : Job? = null
    var episodeState by mutableStateOf("")

    fun searchShows(searchTerm: String){
        homeUiState = homeUiState.copy(searchTerm = searchTerm)
        searchShowsJob?.cancel()
        searchShowsJob = viewModelScope.launch{
            delay(1000L)
            try {
                val results = showsRepository.searchShows(searchTerm)
                homeUiState = homeUiState.copy(results = results)
            } catch(e: IOException){

            }

        }
    }

    fun changeTheme(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.isDarkTheme(value)
        }
    }

    fun saveShow(showId: Int, showName: String){
        viewModelScope.launch{
            val show = Show(showId= showId, name = showName)
            localRepository.saveToDb(show)
        }
    }

    fun deleteShow(show: Show){
        viewModelScope.launch {
            localRepository.deleteShow(show)
        }
    }
    fun getShow(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val show = localRepository.getShow(id)
            deleteShow(show)
        }
    }
    fun getEpisode(showId: Int) {
        episodeState = ""
        viewModelScope.launch {
            val showJob = async {
                showsRepository.getShow(showId)
            }
            val show = showJob.await()
            val episodeDate = show._links?.nextepisode?.href
            if ( episodeDate == "" || episodeDate == null) {
                episodeState = "No upcoming Episodes"
                return@launch
            }
            val episode  = showsRepository.getEpisodeInfo(episodeDate.split("episodes/")[1])
            val date = episode.airdate ?: ""
            val summary = episode.summary?.removeHtml()
            episodeState = "${date} - ${summary}"
            Log.d("Coroutinesss","here")
        }

    }






}