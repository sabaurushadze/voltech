package com.tbc.search.presentation.screen.item_details

import com.tbc.search.presentation.model.favorite.UiFavorite
import com.tbc.search.presentation.model.feed.UiFeedItem

data class ItemDetailsState(
    val isLoading: Boolean = false,
    val itemDetails: UiFeedItem? = null,
    val uid: String = "",
    val favoriteItem: List<UiFavorite> = emptyList(),
    val selectedImage: Int = 0
)
