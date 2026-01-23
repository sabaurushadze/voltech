package com.tbc.search.presentation.screen.search

import androidx.annotation.StringRes

sealed interface SearchSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : SearchSideEffect
    data class NavigateToFeed(val query: String) : SearchSideEffect
}