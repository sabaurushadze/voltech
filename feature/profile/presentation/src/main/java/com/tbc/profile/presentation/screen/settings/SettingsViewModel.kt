package com.tbc.profile.presentation.screen.settings

import com.tbc.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
) : BaseViewModel<SettingsState, SettingsSideEffect, SettingsEvent>(SettingsState()) {

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.NavigateBackToProfile -> navigateBackToProfile()
        }
    }

    private fun navigateBackToProfile() {
        emitSideEffect(SettingsSideEffect.NavigateBackToProfile)
    }
}