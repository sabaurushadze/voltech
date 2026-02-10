package com.tbc.selling.presentation.screen.add_item

import androidx.annotation.StringRes

sealed interface AddItemSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : AddItemSideEffect
    data object NavigateBackToMyItems : AddItemSideEffect
    data object LaunchGallery : AddItemSideEffect
}