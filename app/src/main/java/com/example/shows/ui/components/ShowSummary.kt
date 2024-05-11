package com.example.shows.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shows.R
import com.example.shows.util.removeHtml

@Composable
fun ShowSummary(summary:String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.story_line),
            style = MaterialTheme.typography.titleMedium

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = summary.removeHtml()
        )

    }
}

@Preview
@Composable
fun ShowSummaryPreview() {
    Surface(){
        ShowSummary(
            summary = stringResource(id = R.string.show_summary_placeholder),
            modifier = Modifier.padding(8.dp)
        )
    }
}