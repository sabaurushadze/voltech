package com.tbc.search.presentation.screen.add_to_cart

import androidx.annotation.StringRes

sealed interface AddToCartSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : AddToCartSideEffect
}