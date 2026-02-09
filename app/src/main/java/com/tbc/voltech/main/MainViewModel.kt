package com.tbc.voltech.main

import androidx.lifecycle.viewModelScope
import com.tbc.auth.domain.user_info.GetUserAuthStateUseCase
import com.tbc.auth.domain.user_info.IsUserAuthenticatedUseCase
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.profile.domain.usecase.settings.GetSavedThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSavedThemeUseCase: GetSavedThemeUseCase,
    private val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase,
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase
) : BaseViewModel<MainState, Unit, MainEvent>(MainState()) {

    init {
        observeTheme()
        checkIfAuthorized()
        observeAuthState()
    }

    override fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.OnSuccessfulAuth -> {
                updateState { copy(isAuthorized = true) }
            }
            is MainEvent.OnUpdateTopBarState -> updateState { copy(topBarState = event.value) }

        }
    }

    private fun observeAuthState() {
        viewModelScope.launch {
            getUserAuthStateUseCase()
                .collect { isAuthenticated ->
                    if (!isAuthenticated) {
                        updateState { copy(isAuthorized = false) }
                    }
                }
        }
    }

    private fun checkIfAuthorized() {
        viewModelScope.launch {
            val isAuthorized = isUserAuthenticatedUseCase()
            updateState { copy(isAuthorized = isAuthorized) }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            getSavedThemeUseCase().collect {
                updateState { copy(themeOption = it) }
            }
        }
    }
}