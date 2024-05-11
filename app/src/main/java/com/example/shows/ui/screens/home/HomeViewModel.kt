package com.example.shows.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shows.data.network.ShowsRepository
import com.example.shows.data.local.LocalRepository
import com.example.shows.data.local.Show
import com.example.shows.data.local.UserPreferencesRepository
import com.example.shows.data.network.response.SearchShow
import com.example.shows.data.network.response.toLocalShow
import com.example.shows.ui.screens.HomeUiState
import com.example.shows.util.removeHtml
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface LoadingStates {
    object Success: LoadingStates
    object Loading: LoadingStates
    object Error: LoadingStates
}

@HiltViewModel
class HomeViewModel @Inject constructor(val showsRepository: ShowsRepository, val localRepository: LocalRepository, val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    var loadingState: LoadingStates by mutableStateOf(
        LoadingStates.Success
    )
    var popFromInnerBackStack = mutableStateOf(false)
    var detailPagesTitle = mutableStateOf("")
    var appBarToShow = mutableStateOf<ScaffoldAppBar?>(ScaffoldAppBar.BrandAppBar)
    val isDarkTheme = userPreferencesRepository.isDarkTheme.stateIn(scope=viewModelScope,started = SharingStarted.WhileSubscribed(5_000L),initialValue = true)
    var homeUiState by mutableStateOf(HomeUiState())
    var removeShow by mutableStateOf<Show?>(null)
    val favorites = localRepository.getShows().stateIn( scope = viewModelScope,started = SharingStarted.WhileSubscribed(5000L), initialValue = listOf<Show>())
    var searchShowsJob : Job? = null
    var episodeState by mutableStateOf("")
    var currentShow by mutableStateOf<SearchShow?>(null)

    fun searchShows(){
        val searchTerm = homeUiState.searchTerm
        loadingState = LoadingStates.Loading
        searchShowsJob?.cancel()
        searchShowsJob = viewModelScope.launch{
            try {
                val results = showsRepository.searchShows(searchTerm)
                homeUiState = homeUiState.copy(results = results)
                loadingState = LoadingStates.Success
            } catch(e: IOException){
                loadingState = LoadingStates.Error
            }
        }
    }

    fun onValueChange(text: String) {
        homeUiState = homeUiState.copy(searchTerm = text)
    }

    fun setScaffoldAppBar(appBar: String){
        when{
            appBar == "home" -> {
                appBarToShow.value = ScaffoldAppBar.BrandAppBar
            }
            appBar == "detailPage" -> {
                appBarToShow.value = ScaffoldAppBar.BackAppBar
            }

            appBar == "favorites" -> {
                appBarToShow.value = ScaffoldAppBar.FavoriteAppBar
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
            val show = Show(id= showId, name = showName)
            localRepository.saveToDb(show)
        }
    }

    fun deleteShow(show: Show){
        viewModelScope.launch {
            localRepository.deleteShow(show)
        }
    }
    fun getEpisode(showId: Int) {
        episodeState = ""
        viewModelScope.launch {
            val showJob = async {
                showsRepository.getShow(showId)
            }
            val show = showJob.await()
            val episodeDate = show.links?.nextEpisode?.href
            if ( episodeDate == "" || episodeDate == null) {
                episodeState = "No upcoming Episodes"
                return@launch
            }
            val episode  = showsRepository.getEpisodeInfo(episodeDate.split("episodes/")[1])
            val date = episode.airdate ?: ""
            val summary = episode.summary?.removeHtml()
            episodeState = "${date} - ${summary}"

        }
    }
    fun changeCurrentShow(show: SearchShow) {
        currentShow = show
    }

    fun onClickFollow(inDb: Boolean,show: SearchShow) {
        if(inDb){
            deleteShow(show.toLocalShow())
        } else {
            saveShow(show.id!!,show.name!!)
        }
    }

}

