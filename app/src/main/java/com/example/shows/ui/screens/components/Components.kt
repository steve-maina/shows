package com.example.shows.ui.screens.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shows.R
import com.example.shows.network.response.searchresult.Show
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchShow(getShow: (Int)-> Unit,inDb: Boolean,deleteShow: (com.example.shows.data.local.Show)-> Unit,show: Show, saveShow: (Int, String)-> Unit ,modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    var job: Job? = null
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    Card(modifier = Modifier
        .height(115.dp)
        .fillMaxWidth()
        .padding(8.dp)
        ) {
        if(showDialog){
            AlertDialog(onDismissRequest = { showDialog = false }) {
                Text(show?.summary?.removeHtml() ?: "No summary")
            }
        }
        Row(modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(show.image?.medium)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.weight(2f)) {
                Text(show.name ?: "Unknown title")
            }
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = null,
                modifier = Modifier.clickable {
                    showDialog = true
                }
            )
            Button(onClick = {
                if (inDb){
                    val showId = show.id!!
                    getShow(showId)
                } else {
                    saveShow(show.id!!, show.name!!)
                }
            }){
                Text( if (!inDb) "Add" else "remove")
            }

        }
    }
}

fun String.removeHtml(): String{
    return Jsoup.parse(this).text()
}

fun Show.toLocalShow(): com.example.shows.data.local.Show{
    return com.example.shows.data.local.Show(
        showId = id!!,
        name = name!!
    )
}