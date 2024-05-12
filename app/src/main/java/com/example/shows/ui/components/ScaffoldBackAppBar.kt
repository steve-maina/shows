package com.example.shows.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shows.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBackAppBar(
    navController: NavHostController,
    onClick: (String,String,NavHostController?) -> Unit,
    title: String
) {
    TopAppBar(title = {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                navController.navigateUp()
                onClick("home", "SHOWS",null)
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack,contentDescription = "Go back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    })

}

@Preview
@Composable
fun BackAppBar() {
    Surface(
        modifier = Modifier
            .height(75.dp)
            .width(500.dp)
    ) {
        ScaffoldBackAppBar(
            navController = rememberNavController(),
            onClick = {s1,s2,nc ->},
            title = stringResource(id = R.string.show_name_placeholder)
        )
    }
}