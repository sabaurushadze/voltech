package com.tbc.search.presentation.screen.item_details

import com.tbc.search.presentation.model.feed.UiFeedItem

data class ItemDetailsState(
    val isLoading: Boolean = false,
    val itemDetails: UiFeedItem? = null,
    val selectedImage: Int = 0
)