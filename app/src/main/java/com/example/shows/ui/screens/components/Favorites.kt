package com.example.shows.ui.screens.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.shows.R
import com.example.shows.data.local.Show
import com.example.shows.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorite(episodeState: String,getEpisode: (Int) -> Unit,show: Show, deleteShow: (Show)-> Unit,modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showDialog by remember {
        mutableStateOf(false)
    }
    if(episodeState != "" && showDialog){
        AlertDialog(onDismissRequest = {
            showDialog = false
        }) {
            Text(episodeState)
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            ){
        Text(show.name,Modifier.weight(1f))
        Button(onClick = {
            showDialog = true
            scope.launch{
                getEpisode(show.id)
            } }, modifier = Modifier.weight(1f)) {
            Text(stringResource(id = R.string.next_episode))
        }
        Icon(
            painter = painterResource(R.drawable.delete),
            contentDescription = null,
            modifier = Modifier.clickable {
                deleteShow(show)
            }

        )
    }
}

@Composable
fun Favorites(viewModel: HomeViewModel, episodeState: String, getEpisode: (Int) -> Unit, deleteShow: (Show) -> Unit, favoritesList: StateFlow<List<Show>>, modifier:Modifier = Modifier) {
    viewModel.setScaffoldAppBar("favorites")
    val favorites by favoritesList.collectAsState()
    if(favorites.isEmpty()){
        Box(modifier = Modifier.fillMaxSize()){
            Text("No favorites", modifier = Modifier.align(Alignment.Center))
        }
        return
    }
    LazyColumn {
        items(favorites){it ->
            Favorite(episodeState = episodeState, getEpisode = getEpisode, show = it, deleteShow = deleteShow)
        }
    }
}

