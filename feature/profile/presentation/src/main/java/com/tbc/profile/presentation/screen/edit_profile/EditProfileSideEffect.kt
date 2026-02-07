package com.tbc.profile.presentation.screen.edit_profile

import androidx.annotation.StringRes

sealed interface EditProfileSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : EditProfileSideEffect
    data object LaunchGallery : EditProfileSideEffect
    data object NavigateToProfile : EditProfileSideEffect
}