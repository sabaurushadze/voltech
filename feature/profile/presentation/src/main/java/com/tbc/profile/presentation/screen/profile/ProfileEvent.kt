package com.tbc.profile.presentation.screen.profile

sealed class ProfileEvent {
    data object NavigateToSettings : ProfileEvent()
    data object NavigateToUserDetails : ProfileEvent()
    data object GetUserInfo : ProfileEvent()
}