package com.tbc.search.presentation.screen.feed

import androidx.annotation.StringRes

sealed interface FeedSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : FeedSideEffect
    data class NavigateToItemDetails(val query: String) : FeedSideEffect
}