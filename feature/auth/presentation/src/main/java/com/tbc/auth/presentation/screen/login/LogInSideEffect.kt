package com.tbc.auth.presentation.screen.login

import androidx.annotation.StringRes

sealed interface LogInSideEffect {
    data object Success : LogInSideEffect
    data object NavigateToRegister : LogInSideEffect
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : LogInSideEffect

}