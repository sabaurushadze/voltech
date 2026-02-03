package com.tbc.voltech.main

import com.tbc.core_ui.components.topbar.TopBarState

sealed interface MainEvent {
    data object OnSuccessfulAuth : MainEvent
    data class OnUpdateTopBarState(val value: TopBarState) : MainEvent

}

