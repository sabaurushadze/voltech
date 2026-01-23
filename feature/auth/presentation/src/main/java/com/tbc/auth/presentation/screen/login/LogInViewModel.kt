package com.tbc.auth.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.tbc.auth.domain.usecase.ValidateEmailUseCase
import com.tbc.auth.domain.usecase.ValidatePasswordUseCase
import com.tbc.auth.domain.usecase.login.LogInWithEmailAndPasswordUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInWithEmailAndPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel<LogInState, LogInSideEffect, LogInEvent>(LogInState()) {

    override fun onEvent(event: LogInEvent) {
        when (event) {

            is LogInEvent.LogIn -> logIn()
            is LogInEvent.NavigateToRegister -> navigateToRegister()
            is LogInEvent.EmailChanged -> updateEmail(event.email)
            is LogInEvent.PasswordChanged -> updatePassword(event.password)
            LogInEvent.PasswordVisibilityChanged -> updatePasswordVisibility()
        }
    }

    private fun updatePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun navigateToRegister() {
        emitSideEffect(LogInSideEffect.NavigateToRegister)
    }

    private fun logIn() = viewModelScope.launch {
        updateState { copy(
            isLoading = true,
            showPasswordError = false,
            showEmailError = false
        ) }
        val isEmailValid = validateEmailUseCase(state.value.email)
        val isPasswordValid = validatePasswordUseCase(state.value.password)
        if (isEmailValid && isPasswordValid) {
            logInUseCase(email = state.value.email, password = state.value.password)
                .onSuccess {
                    emitSideEffect(LogInSideEffect.Success)
                    updateState { copy(isLoading = false) }

                }
                .onFailure {
                    emitSideEffect(LogInSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                    updateState { copy(isLoading = false) }
                }
        } else {
            updateState {
                copy(
                    showEmailError = !isEmailValid,
                    showPasswordError = !isPasswordValid,
                    isLoading = false
                )
            }
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