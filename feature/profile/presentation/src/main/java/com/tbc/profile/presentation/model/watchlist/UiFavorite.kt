package com.tbc.profile.presentation.model.watchlist

data class UiFavorite(
    val id: Int,
    val favoriteId: Int,
    val title: String,
    val price: String,
    val images: List<String>,
    val isSelected: Boolean = false,
)