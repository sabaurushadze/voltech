package com.tbc.voltech.main

import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.profile.domain.model.settings.VoltechThemeOption

data class MainState(
    val topBarState: TopBarState = TopBarState(),
    val themeOption: VoltechThemeOption = VoltechThemeOption.SYSTEM,
    val isAuthorized: Boolean? = null,
){
    val isLoading:Boolean
        get() = isAuthorized == null
}
