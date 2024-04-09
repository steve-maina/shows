package com.example.shows


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shows.ui.screens.home.HomeViewModel
import com.example.shows.ui.screens.home.Home
import com.example.shows.ui.theme.ShowsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel = hiltViewModel<HomeViewModel>()
                    val favorites by viewModel.favorites.collectAsState()
                    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
                    Home(viewModel::changeTheme,isDarkTheme,viewModel::getShow,viewModel.episodeState,viewModel::getEpisode,viewModel.homeUiState, viewModel::searchShows, viewModel::saveShow,viewModel::deleteShow,favorites)
                    //ShowsApp()
                }
            }
        }
    }
}

