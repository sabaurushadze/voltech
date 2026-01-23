package com.tbc.auth.presentation.screen.register

sealed class RegisterEvent {
    data object Register : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data object PasswordVisibilityChanged : RegisterEvent()

}