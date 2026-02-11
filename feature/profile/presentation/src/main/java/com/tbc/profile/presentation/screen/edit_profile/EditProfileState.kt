package com.tbc.profile.presentation.screen.edit_profile

import android.net.Uri
import com.tbc.core.presentation.model.UiUser

data class EditProfileState(
    val user: UiUser? = null,

    val isLoading: Boolean = false,
    val userName: String? = "",
    val showUsernameError: Boolean = false,

    val selectedProfileEdit: Boolean = false,
    val selectedImageUri: Uri? = null
) {
    val saveButtonEnabled
        get() = !isLoading && (selectedImageUri != null || userName != user?.name)
}