package com.tbc.auth.presentation.screen.register

import androidx.lifecycle.viewModelScope
import com.tbc.auth.domain.usecase.ValidateEmailUseCase
import com.tbc.auth.domain.usecase.register.RegisterWithEmailAndPasswordUseCase
import com.tbc.auth.domain.usecase.register.ValidateRegistrationPasswordUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import com.tbc.profile.domain.usecase.edit_profile.UpdateUserNameUseCase
import com.tbc.profile.domain.usecase.edit_profile.ValidateUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterWithEmailAndPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateRegistrationPasswordUseCase: ValidateRegistrationPasswordUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
) : BaseViewModel<RegisterState, RegisterSideEffect, RegisterEvent>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            RegisterEvent.Register -> register()
            RegisterEvent.PasswordVisibilityChanged -> updatePasswordVisibility()
            is RegisterEvent.UsernameChanged -> updateUsername(event.username)
        }
    }

    private fun updatePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun register() = viewModelScope.launch {
        updateState { copy(
            isLoading = true,
            showPasswordError = false,
            showEmailError = false
        ) }
        val isEmailValid = validateEmailUseCase(state.value.email)
        val isPasswordValid = validateRegistrationPasswordUseCase(state.value.password)
        val isUserNameValid = validateUserNameUseCase(state.value.username)

        if (isEmailValid && isPasswordValid && isUserNameValid) {
            registerUseCase(email = state.value.email, password = state.value.password)
                .onSuccess {
                    updateState { copy(isLoading = false) }
                    updateUserNameUseCase(state.value.username)
                    emitSideEffect(RegisterSideEffect.Success)
                }
                .onFailure {
                    emitSideEffect(RegisterSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                    updateState { copy(isLoading = false) }
                }
        } else {
            updateState {
                copy(
                    showEmailError = !isEmailValid,
                    showPasswordError = !isPasswordValid,
                    showUsernameError = !isUserNameValid,
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

    private fun updateUsername(username: String) {
        updateState {
            copy(username = username)
        }
    }
}