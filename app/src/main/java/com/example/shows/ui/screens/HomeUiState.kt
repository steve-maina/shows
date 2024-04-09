package com.example.shows.ui.screens

import com.example.shows.network.response.searchresult.SearchResultItem

data class HomeUiState(
    val searchTerm: String = "",
    val results: List<SearchResultItem> = listOf(),
)