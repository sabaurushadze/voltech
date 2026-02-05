package com.tbc.home.presentation.screen

import com.tbc.home.presentation.screen.model.UiCategoryItem

data class HomeState (
    val isLoading: Boolean = false,
    val categoryList: List<UiCategoryItem> = emptyList()
)