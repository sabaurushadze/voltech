package com.tbc.search.presentation.screen.item_details

import com.tbc.core.presentation.model.UiUser
import com.tbc.search.presentation.model.favorite.UiFavorite
import com.tbc.search.presentation.model.feed.UiFeedItem
import com.tbc.search.presentation.model.item_details.UiSeller

data class ItemDetailsState(
    val user: UiUser = UiUser(
        uid = "",
        name = "",
        photoUrl = ""
    ),
    val seller: UiSeller? = null,

    val isLoading: Boolean = false,
    val itemDetails: UiFeedItem? = null,
    val itemId: Int = 0,
    val recentlyItemsId: List<Int> = emptyList(),
    val favoriteItem: List<UiFavorite> = emptyList(),
    val selectedImage: Int = 0,
    val cartItemIds: List<Int> = emptyList(),
    val isInCart: Boolean = false,


    val previewStartIndex: Int? = null
    )