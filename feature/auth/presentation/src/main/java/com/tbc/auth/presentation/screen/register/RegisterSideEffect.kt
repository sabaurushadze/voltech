package com.tbc.auth.presentation.screen.register

import androidx.annotation.StringRes

sealed interface RegisterSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : RegisterSideEffect
    data object Success : RegisterSideEffect
}