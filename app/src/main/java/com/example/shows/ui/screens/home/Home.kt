package com.example.shows.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.example.shows.R
import com.example.shows.data.local.Show

import com.example.shows.ui.screens.HomeUiState
import com.example.shows.ui.screens.components.Favorite
import com.example.shows.ui.screens.components.SearchShow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(
    changeTheme: (Boolean) -> Unit,
    isDarkTheme:Boolean,
    getShow: (Int)-> Unit,
    episodeState: String,
    getEpisode: (Int) -> Unit,
    uiState: HomeUiState, onValueChange:(String) -> Unit,
    saveShow: (Int, String)-> Unit,
    deleteShow: (Show)-> Unit,
    favorites: List<Show>,
    modifier: Modifier = Modifier){
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = uiState.searchTerm,
            onValueChange = {
                onValueChange(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically ,modifier = Modifier.fillMaxWidth()){
            Text("Dark Theme?")
            Switch(checked = isDarkTheme , onCheckedChange = {darkTheme->
                changeTheme(darkTheme)
            })

        }

        Text("${uiState.results.size}")
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(uiState.results){ searchShow ->
                SearchShow(getShow,favorites.find { it.showId == searchShow.show.id!!} != null,deleteShow, show = searchShow.show,saveShow)
            }
            item{
                Text("Favorites")
            }
            items(favorites){show ->
                Favorite(episodeState,getEpisode,show,deleteShow)
            }
        }
        
    }
}