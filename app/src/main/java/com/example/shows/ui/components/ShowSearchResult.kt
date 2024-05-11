package com.example.shows.ui.components

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shows.R
import com.example.shows.data.local.Show
import com.example.shows.data.network.response.SearchShow
import com.example.shows.data.network.response.ShowImage
import com.example.shows.data.network.response.toLocalShow
import com.example.shows.ui.theme.ShowsTheme

@Composable
fun ShowSearchResult(onClickItem: () -> Unit, changeCurrentShow: (SearchShow) -> Unit, deleteShow: (Show)-> Unit, inDb: Boolean, show: SearchShow, saveShow: (Int, String)-> Unit, modifier: Modifier = Modifier) {

    Card(modifier = modifier
        .height(115.dp)
        .fillMaxWidth()
        .clickable {
            onClickItem()
            changeCurrentShow(show)
        }
    ) {

        Row(
            modifier
                .fillMaxSize()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(show.image?.medium)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                show.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (inDb){
                    val showId = show.id!!
                    deleteShow(show.toLocalShow())
                } else {
                    if(show.id != null && show.name != null && show.name != "") saveShow(show.id, show.name)
                }
            },
                shape = MaterialTheme.shapes.small
            ){
                Text( if (!inDb) "Add" else "remove")
            }

        }
    }
}

@Preview
@Composable
fun ShowSearchResultPreview() {

       ShowSearchResult(
           onClickItem = { },
           changeCurrentShow = {},
           deleteShow = {},
           inDb = false,
           show = SearchShow(name = stringResource(R.string.show_name_placeholder)),
           saveShow = { id, name -> },
           modifier = Modifier.width(720.dp)
       )
}
