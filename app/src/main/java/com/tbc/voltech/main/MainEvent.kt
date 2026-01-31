package com.tbc.voltech.main

sealed interface MainEvent {
    data object OnSuccessfulAuth : MainEvent
}

