package com.tbc.profile.presentation.screen.settings

import com.tbc.profile.domain.model.settings.VoltechThemeOption

sealed class SettingsEvent {
    data object NavigateBackToProfile : SettingsEvent()
    data class ThemeChanged(val theme: VoltechThemeOption) : SettingsEvent()
    data object SignOut : SettingsEvent()
}