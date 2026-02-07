package com.tbc.profile.presentation.screen.profile

import com.tbc.profile.presentation.model.edit_profile.UiUser


data class ProfileState(
    val isLoading: Boolean = false,
    val user: UiUser? = null,
)