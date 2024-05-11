package com.example.shows.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shows.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    searchTerm: String,
    onValueChange: (String) -> Unit,
    doSearch: () -> Unit,
    modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = searchTerm,
        onValueChange = {
            onValueChange(it) },
        modifier = modifier,
        placeholder = {
            Text(text = stringResource(id = R.string.search_placeholder))
        },
        maxLines = 1,
        shape = RoundedCornerShape(50.dp),
        leadingIcon = {
            Icon(painter = painterResource(R.drawable.search), contentDescription = null)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                doSearch()
                keyboardController?.hide()
            }
        )
    )
}

@Preview
@Composable
fun SearchBoxPreview() {
    SearchBox(
        searchTerm = "",
        onValueChange = {},
        doSearch = {  },
        modifier = Modifier.width(500.dp).height(55.dp).padding(horizontal = 16.dp)
    )
}