package com.example.shows.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.shows.R
import com.example.shows.data.local.Show
import com.example.shows.ui.components.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

@Composable
fun Favorites(setScaffoldAppBar:(String) -> Unit, episodeState: String, getEpisode: (Int) -> Unit, deleteShow: (Show) -> Unit, favoritesList: StateFlow<List<Show>>, modifier: Modifier = Modifier) {

    setScaffoldAppBar("favorites")
    val favorites by favoritesList.collectAsState()
    if(favorites.isEmpty()){
        Box(modifier = Modifier.fillMaxSize()){
            Text("No favorites", modifier = Modifier.align(Alignment.Center))
        }
        return
    }
    Column {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favorites) { it ->
                Favorite(
                    episodeState = episodeState,
                    getEpisode = getEpisode,
                    show = it,
                    deleteShow = deleteShow,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoritesPreview(@PreviewParameter(FavoritesListFlowProvider::class) favoritesList: MutableStateFlow<List<Show>>) {
    Favorites(
        setScaffoldAppBar = {},
        episodeState = stringResource(id = R.string.next_episode_date),
        getEpisode = {},
        deleteShow = {},
        favoritesList = favoritesList
    )
}

class FavoritesListFlowProvider: PreviewParameterProvider<MutableStateFlow<List<Show>>> {
    override val values: Sequence<MutableStateFlow<List<Show>>>
        get() = sequenceOf(
            MutableStateFlow( List(100) {
                Show(id = it,name= "show #$it")
            })
        )
}