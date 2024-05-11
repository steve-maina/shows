package com.example.shows.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shows.data.local.Show
import com.example.shows.data.network.response.SearchShow
import com.example.shows.ui.components.ImageDetail
import com.example.shows.ui.components.ShowSummary
import com.example.shows.ui.screens.home.HomeViewModel
import com.example.shows.util.removeHtml


@Composable
fun DetailPage(
    viewModel: HomeViewModel,
    currentShow: SearchShow?,
    showSummary: String, showUrl:String?, favorites: List<Show>, onClickFollow: (Boolean, SearchShow) -> Unit, modifier: Modifier = Modifier) {
    val inDatabase = favorites.find { it.id == viewModel.currentShow?.id } != null
    viewModel.detailPagesTitle.value = currentShow?.name ?: ""
    viewModel.setScaffoldAppBar("detailPage")
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
       if(showUrl != "" && showUrl != null){
           ImageDetail(showUrl = showUrl)
       }
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Button(
                onClick = {onClickFollow(inDatabase,currentShow!!) },
                modifier = Modifier.padding(8.dp),
                shape = MaterialTheme.shapes.small
            ){
                Text(text = if(inDatabase) "unfollow" else "follow")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ShowSummary(summary = showSummary)
    }
}