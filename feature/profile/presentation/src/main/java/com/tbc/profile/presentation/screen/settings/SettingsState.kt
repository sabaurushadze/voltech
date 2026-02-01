package com.tbc.profile.presentation.screen.settings

import com.tbc.profile.domain.model.settings.VoltechThemeOption

data class SettingsState(
    val isLoading: Boolean = false,
    val themeOption: VoltechThemeOption = VoltechThemeOption.SYSTEM
)