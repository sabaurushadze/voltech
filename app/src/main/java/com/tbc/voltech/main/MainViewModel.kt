package com.tbc.voltech.main

import androidx.lifecycle.viewModelScope
import com.tbc.domain.user_info.GetUserAuthStateUseCase
import com.tbc.domain.user_info.IsUserAuthenticatedUseCase
import com.tbc.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase,
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase
) : BaseViewModel<MainActivityState, MainSideEffect, MainActivityEvent>(MainActivityState()) {

    init {
        checkIfAuthorized()
        observeAuthState()
    }

    override fun onEvent(event: MainActivityEvent) {
        when (event) {
            MainActivityEvent.OnSuccessfulAuth -> {
                updateState { copy(isAuthorized = true) }
            }
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

}