package com.tbc.profile.presentation.screen.settings

import androidx.lifecycle.viewModelScope
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.profile.domain.model.settings.VoltechThemeOption
import com.tbc.profile.domain.usecase.settings.GetSavedThemeUseCase
import com.tbc.profile.domain.usecase.settings.SaveThemeUseCase
import com.tbc.profile.domain.usecase.settings.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSavedThemeUseCase: GetSavedThemeUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    private val signOutUseCase: SignOutUseCase
) : BaseViewModel<SettingsState, SettingsSideEffect, SettingsEvent>(SettingsState()) {

    init {
        observeTheme()
    }
    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ThemeChanged -> saveTheme(event.theme)
            SettingsEvent.NavigateBackToProfile -> navigateBackToProfile()
            SettingsEvent.SignOut -> signOut()
        }
    }
    private fun signOut() {
        signOutUseCase()
    }
    private fun observeTheme() {
        viewModelScope.launch {
            getSavedThemeUseCase().collect { theme ->
                updateState { copy(themeOption = theme) }
            }
        }
    }

    private fun saveTheme(theme: VoltechThemeOption) {
        viewModelScope.launch {
            saveThemeUseCase(theme)
        }
    }

    private fun navigateBackToProfile() {
        emitSideEffect(SettingsSideEffect.NavigateBackToProfile)
    }
}