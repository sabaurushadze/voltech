package com.tbc.presentation.register

sealed class RegisterEvent {
    data object Register : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
}