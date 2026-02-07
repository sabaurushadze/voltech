package com.tbc.profile.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.GetCurrentUserUseCase
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.profile.presentation.mapper.edit_profile.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(ProfileState()) {

    init {
        getCurrentUser()
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.GetUserInfo -> {}
            ProfileEvent.NavigateToSettings -> navigateToSettings()
            ProfileEvent.NavigateToUserDetails -> navigateToUserDetails()
        }
    }

    private fun navigateToSettings() {
        emitSideEffect(ProfileSideEffect.NavigateToSettings)
    }

    private fun navigateToUserDetails() {
        emitSideEffect(ProfileSideEffect.NavigateToUserDetails)
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase()?.toPresentation()
            updateState { copy(user = currentUser) }
        }
    }
}