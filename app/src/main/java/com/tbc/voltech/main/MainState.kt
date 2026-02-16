package com.tbc.voltech.main

import com.tbc.profile.domain.model.settings.VoltechThemeOption

data class MainState(
    val themeOption: VoltechThemeOption? = null,
    val isAuthorized: Boolean? = null,
) {
    val isLoading: Boolean
        get() = isAuthorized == null || themeOption == null
}