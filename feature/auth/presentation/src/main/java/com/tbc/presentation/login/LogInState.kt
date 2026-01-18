package com.tbc.presentation.login

data class LogInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)