package com.tbc.search.presentation.screen.search

import com.tbc.search.presentation.model.UiSearchItem

data class SearchState(
    val query: String = "",
    val titles: List<UiSearchItem> = listOf(),
    val isLoading: Boolean = false,
)