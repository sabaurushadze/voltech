package com.tbc.presentation.login

sealed class LogInEvent {
    data object LogIn : LogInEvent()
    data object NavigateToRegister : LogInEvent()
    data class EmailChanged(val email: String) : LogInEvent()
    data class PasswordChanged(val password: String) : LogInEvent()
}