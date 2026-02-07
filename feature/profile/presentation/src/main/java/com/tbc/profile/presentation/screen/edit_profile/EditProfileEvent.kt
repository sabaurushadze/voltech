package com.tbc.profile.presentation.screen.edit_profile

import android.net.Uri

sealed class EditProfileEvent {

    data object OnLaunchGallery : EditProfileEvent()
    data class OnPhotoSelected(val imageUri: Uri) : EditProfileEvent()
    data class SavePhotoInStorage(val imageUri: Uri?) : EditProfileEvent()
    data class DeletePhotoFromStorage(val imageUrl: String?) : EditProfileEvent()

    data object NavigateBackToProfile : EditProfileEvent()
    data object ShowProfileEditSheet : EditProfileEvent()
    data object HideProfileEditSheet : EditProfileEvent()

    data class UserNameChanged(val userName: String) : EditProfileEvent()
    data class SaveUsername(val userName: String) : EditProfileEvent()

}