package com.tbc.profile.presentation.screen.profile

import androidx.annotation.StringRes

sealed interface ProfileSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : ProfileSideEffect
    data object NavigateToSettings : ProfileSideEffect
    data object NavigateToUserDetails : ProfileSideEffect
}