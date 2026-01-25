package com.tbc.search.presentation.screen.feed

import com.tbc.search.domain.model.feed.FeedQuery

data class FeedState(
    val query: FeedQuery = FeedQuery(),
    val isLoading: Boolean = false,
)