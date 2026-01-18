package com.tbc.presentation.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)