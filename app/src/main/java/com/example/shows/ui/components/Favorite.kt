package com.example.shows.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shows.R
import com.example.shows.data.local.Show
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorite(episodeState: String,getEpisode: (Int) -> Unit,show: Show, deleteShow: (Show)-> Unit,modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var showDialog by remember {
        mutableStateOf(false)
    }
    if(episodeState != "" && showDialog){
        AlertDialog(onDismissRequest = {
            showDialog = false
        }) {
            Text(text = episodeState)
        }
    }
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                show.name,
                Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Button(onClick = {
                showDialog = true
                scope.launch {
                    getEpisode(show.id)
                }
            }, modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(id = R.string.next_episode),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = null,
                modifier = Modifier.clickable {
                    deleteShow(show)
                }

            )
        }
    }
}



@Preview
@Composable
fun FavoritePreview() {
        Favorite(
            episodeState = stringResource(id = R.string.next_episode),
            getEpisode = {},
            show = Show(name = stringResource(id = R.string.show_name_placeholder), id = 17078),
            deleteShow = {},
            modifier = Modifier.width(500.dp).padding(8.dp)
        )
}
