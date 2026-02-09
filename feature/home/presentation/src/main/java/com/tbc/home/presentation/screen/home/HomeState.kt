package com.tbc.home.presentation.screen.home

import com.tbc.home.presentation.model.category.UiCategoryItem
import com.tbc.home.presentation.model.recently_viewed.UiRecentlyItem

data class HomeState (
    val isLoading: Boolean = false,
    val categoryList: List<UiCategoryItem> = emptyList(),
    val recentlyItemsId: List<Int> = emptyList(),
    val recentlyViewedItems: List<UiRecentlyItem> = emptyList()
)