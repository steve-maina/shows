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
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.shows.data.Constants
import com.example.shows.data.local.UserPreferencesRepository
import com.example.shows.ui.screens.home.HomeViewModel
import com.example.shows.ui.screens.home.Home
import com.example.shows.ui.screens.home.ShowsApp
import com.example.shows.ui.theme.ShowsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var darkTheme: UserPreferencesRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = darkTheme.isDarkTheme.collectAsState(initial = false)
            ShowsTheme(darkTheme = darkTheme.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<HomeViewModel>()
                    ShowsApp(navController,viewModel)
                }
            }
        }
    }
}

