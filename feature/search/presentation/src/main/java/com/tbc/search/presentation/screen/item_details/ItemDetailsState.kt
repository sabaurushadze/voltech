package com.tbc.search.presentation.screen.item_details

import com.tbc.core.presentation.model.UiUser
import com.tbc.search.presentation.model.favorite.UiFavorite
import com.tbc.search.presentation.model.feed.UiFeedItem

data class ItemDetailsState(
    val user: UiUser? = null,

    val isLoading: Boolean = false,
    val itemDetails: UiFeedItem? = null,
//    val uid: String = "",
    val itemId: Int = 0,
    val recentlyItemsId: List<Int> = emptyList(),
    val favoriteItem: List<UiFavorite> = emptyList(),
    val selectedImage: Int = 0
)
