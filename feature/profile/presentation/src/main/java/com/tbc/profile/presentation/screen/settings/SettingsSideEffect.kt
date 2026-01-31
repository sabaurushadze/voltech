package com.tbc.profile.presentation.screen.settings

import androidx.annotation.StringRes

sealed interface SettingsSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : SettingsSideEffect
    data object NavigateBackToProfile : SettingsSideEffect
}