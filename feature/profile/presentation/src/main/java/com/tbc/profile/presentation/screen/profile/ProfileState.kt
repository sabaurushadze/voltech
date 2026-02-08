package com.tbc.profile.presentation.screen.profile

import com.tbc.core.presentation.model.UiUser


data class ProfileState(
    val isLoading: Boolean = false,
    val user: UiUser? = null,
)