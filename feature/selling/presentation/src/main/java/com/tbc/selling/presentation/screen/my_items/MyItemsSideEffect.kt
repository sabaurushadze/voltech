package com.tbc.selling.presentation.screen.my_items

import androidx.annotation.StringRes

sealed interface MyItemsSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : MyItemsSideEffect
    data object NavigateToAddItem : MyItemsSideEffect
}