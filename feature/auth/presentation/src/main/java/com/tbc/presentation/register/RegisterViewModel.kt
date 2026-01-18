package com.tbc.presentation.register

import androidx.lifecycle.viewModelScope
import com.tbc.domain.usecase.register.RegisterWithEmailAndPasswordUseCase
import com.tbc.domain.util.onFailure
import com.tbc.domain.util.onSuccess
import com.tbc.presentation.base.BaseViewModel
import com.tbc.presentation.mapper.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterWithEmailAndPasswordUseCase,
) : BaseViewModel<RegisterState, RegisterSideEffect, RegisterEvent>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            RegisterEvent.Register -> register()
        }
    }

    private fun register() = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        registerUseCase(email = state.value.email, password = state.value.password)
            .onSuccess {
                emitSideEffect(RegisterSideEffect.Success)
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(RegisterSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }

    private fun updateEmail(email: String) {
        updateState {
            copy(email = email)
        }
    }

    private fun updatePassword(password: String) {
        updateState {
            copy(password = password)
        }
    }
}