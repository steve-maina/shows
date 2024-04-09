package com.example.shows.ui.screens.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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
                getEpisode(show.showId)
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

