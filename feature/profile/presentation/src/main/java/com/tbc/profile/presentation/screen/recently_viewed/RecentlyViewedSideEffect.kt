package com.tbc.profile.presentation.screen.recently_viewed

import androidx.annotation.StringRes

sealed interface RecentlyViewedSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : RecentlyViewedSideEffect
    data object NavigateBackToProfile : RecentlyViewedSideEffect
    data class NavigateToItemDetails(val itemId: Int) : RecentlyViewedSideEffect
}