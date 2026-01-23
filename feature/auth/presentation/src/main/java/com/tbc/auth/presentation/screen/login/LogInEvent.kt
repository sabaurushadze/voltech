package com.tbc.auth.presentation.screen.login

sealed class LogInEvent {
    data object LogIn : LogInEvent()
    data object NavigateToRegister : LogInEvent()
    data class EmailChanged(val email: String) : LogInEvent()
    data class PasswordChanged(val password: String) : LogInEvent()
    data object PasswordVisibilityChanged : LogInEvent()

}