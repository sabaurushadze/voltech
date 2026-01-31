package com.tbc.profile.presentation.screen.settings

sealed class SettingsEvent {
    data object NavigateBackToProfile : SettingsEvent()
}