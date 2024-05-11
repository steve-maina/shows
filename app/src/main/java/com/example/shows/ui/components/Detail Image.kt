package com.example.shows.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ImageDetail(showUrl: String, modifier: Modifier = Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(showUrl)
                .crossfade(true)
                .build(),
            modifier = modifier,
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
}
