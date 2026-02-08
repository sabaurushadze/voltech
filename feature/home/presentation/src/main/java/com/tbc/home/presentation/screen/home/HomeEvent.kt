package com.tbc.home.presentation.screen.home

sealed class HomeEvent {
    data object GetCategories: HomeEvent()
    data object GetRecentlyViewedItems: HomeEvent()
}