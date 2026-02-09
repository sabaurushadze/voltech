package com.tbc.auth.presentation.screen.register

data class RegisterState(
    val isLoading: Boolean = false,

    val email: String = "",
    val password: String = "",
    val username: String = "",

    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,
    val showUsernameError: Boolean = false,

    val isPasswordVisible: Boolean = false,
) {
    val isRegisterEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && username.isNotBlank()
}