package com.tbc.profile.presentation.screen.profile

import com.tbc.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(ProfileState()) {

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

//    private fun searchByQuery(query: String) = viewModelScope.launch {
//        updateState { copy(isLoading = true) }
//
//        searchItemByQueryUseCase(query)
//            .onSuccess { titlesDomain ->
//                updateState { copy(titles = titlesDomain.map { it.toPresentation() }) }
//                updateState { copy(isLoading = false) }
//            }
//            .onFailure {
//                emitSideEffect(SearchSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
//                updateState { copy(isLoading = false) }
//            }
//    }
}