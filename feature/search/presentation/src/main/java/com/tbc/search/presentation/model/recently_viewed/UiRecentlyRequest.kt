package com.tbc.search.presentation.model.recently_viewed

data class UiRecentlyRequest (
    val uid: String,
    val itemId: Int,
    val viewedAt: String
)