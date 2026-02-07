package com.tbc.profile.presentation.screen.edit_profile

import android.net.Uri
import com.tbc.profile.presentation.model.edit_profile.UiUser

data class EditProfileState(
    val user: UiUser? = null,

    val isLoading: Boolean = false,
    val userName: String? = "",
    val showUsernameError: Boolean = false,

    val selectedProfileEdit: Boolean = false,
    val selectedImageUri: Uri? = null,
    )