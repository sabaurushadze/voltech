package com.tbc.home.presentation.screen

sealed class HomeEvent {
    data object GetCategories: HomeEvent()
}