package com.tbc.search.presentation.screen.search

sealed class SearchEvent {
    data class SearchByQuery(val query: String) : SearchEvent()
    data class QueryChanged(val query: String) : SearchEvent()
    data class NavigateToFeedWithQuery(val query: String) : SearchEvent()
    data class SaveRecentSearch(val query: String) : SearchEvent()
    data class RemoveRecentSearch(val query: String) : SearchEvent()
}