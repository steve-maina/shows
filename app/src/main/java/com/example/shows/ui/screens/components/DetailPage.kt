package com.example.shows.ui.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shows.data.local.Show
import com.example.shows.network.response.SearchShow
import com.example.shows.ui.screens.home.HomeViewModel


@Composable
fun DetailPage(
    viewModel: HomeViewModel,
    currentShow: SearchShow?,
    showSummary: String, showUrl:String?, favorites: List<Show>, onClickFollow: (Boolean, SearchShow) -> Unit, modifier: Modifier = Modifier) {
    val inDatabase = favorites.find { it.id == viewModel.currentShow?.id } != null
    viewModel.detailPagesTitle.value = currentShow?.name ?: ""
    viewModel.setScaffoldAppBar("detailPage")
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
       if(showUrl != ""){
           AsyncImage(
               model = ImageRequest.Builder(LocalContext.current)
                   .data(showUrl)
                   .crossfade(true)
                   .build(),
               contentDescription = null
           )
       }

        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Button(
                onClick = {onClickFollow(inDatabase,currentShow!!) },
                modifier = Modifier.padding(8.dp)
            ){
                Text(text = if(inDatabase) "unfollow" else "follow")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

       Text(
           text = showSummary.removeHtml()
       )
    }
}