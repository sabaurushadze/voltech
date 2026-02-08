package com.tbc.profile.presentation.screen.watchlist

import androidx.annotation.StringRes

sealed interface WatchlistSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : WatchlistSideEffect
    data object NavigateBackToProfile : WatchlistSideEffect
    data class NavigateToItemDetails(val itemId: Int) : WatchlistSideEffect
}