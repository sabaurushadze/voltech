package com.tbc.search.presentation.screen.search

import com.tbc.search.presentation.model.UiSearchItem

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val titles: List<UiSearchItem> = listOf(),
    val recentSearchList: List<String> = emptyList(),
)