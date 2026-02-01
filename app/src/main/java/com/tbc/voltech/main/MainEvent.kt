package com.tbc.voltech.main

import com.tbc.core.designsystem.components.topbar.TopBarState
sealed interface MainEvent {
    data object OnSuccessfulAuth : MainEvent
    data class OnUpdateTopBarState(val value: TopBarState) : MainEvent

}

