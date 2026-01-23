package com.tbc.auth.presentation.screen.login

data class LogInState(
    val email: String = "",
    val password: String = "",
    val showPasswordError: Boolean = false,
    val showEmailError: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank()
}