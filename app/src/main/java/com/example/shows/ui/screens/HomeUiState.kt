package com.example.shows.ui.screens


import com.example.shows.data.network.response.ShowResult


data class HomeUiState(
    val searchTerm: String = "",
    val results: List<ShowResult> = listOf(),
)