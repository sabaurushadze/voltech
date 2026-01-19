package com.tbc.presentation.register

import com.tbc.presentation.login.LogInEvent

sealed class RegisterEvent {
    data object Register : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data object PasswordVisibilityChanged : RegisterEvent()

}