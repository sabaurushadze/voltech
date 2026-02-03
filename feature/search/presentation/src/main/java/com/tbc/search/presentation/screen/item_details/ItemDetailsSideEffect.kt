package com.tbc.search.presentation.screen.item_details

import androidx.annotation.StringRes

sealed interface ItemDetailsSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : ItemDetailsSideEffect
    data object NavigateBackToFeed : ItemDetailsSideEffect
}