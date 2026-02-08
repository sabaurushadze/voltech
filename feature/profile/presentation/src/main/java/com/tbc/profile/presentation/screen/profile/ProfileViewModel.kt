package com.tbc.profile.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
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
            ProfileEvent.NavigateToWatchlist -> navigateToWatchlist()
        }
    }

    private fun navigateToWatchlist() {
        emitSideEffect(ProfileSideEffect.NavigateToWatchlist)
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