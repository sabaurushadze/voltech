package com.tbc.profile.presentation.mapper.settings

import com.tbc.profile.domain.model.settings.VoltechThemeOption
import com.tbc.resource.R

fun VoltechThemeOption.toStringRes(): Int {
    return when(this) {
        VoltechThemeOption.LIGHT -> R.string.theme_light
        VoltechThemeOption.DARK -> R.string.theme_dark
        VoltechThemeOption.SYSTEM -> R.string.theme_system
    }
}