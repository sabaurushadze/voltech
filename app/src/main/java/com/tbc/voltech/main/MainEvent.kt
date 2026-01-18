package com.tbc.voltech.main

sealed interface MainActivityEvent {
    data object OnSuccessfulAuth : MainActivityEvent
}
