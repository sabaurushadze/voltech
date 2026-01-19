package com.tbc.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val showPasswordError: Boolean = false,
    val showEmailError: Boolean = false,
    val isPasswordVisible: Boolean = false,
) {
    val isRegisterEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank()
}