package com.tbc.search.presentation.screen.feed

sealed class FeedEvent {
    data class GetItems(val query: String) : FeedEvent()

    data class SaveSearchQuery(val query: String) : FeedEvent()
}