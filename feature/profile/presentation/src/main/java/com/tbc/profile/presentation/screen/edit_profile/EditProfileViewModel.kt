package com.tbc.profile.presentation.screen.edit_profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.GetCurrentUserStreamUseCase
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.profile.domain.usecase.edit_profile.DeleteFileUseCase
import com.tbc.profile.domain.usecase.edit_profile.EnqueueFileUploadUseCase
import com.tbc.profile.domain.usecase.edit_profile.UpdateProfilePictureUseCase
import com.tbc.profile.presentation.mapper.edit_profile.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val enqueueFileUploadUseCase: EnqueueFileUploadUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val getCurrentUserStreamUseCase: GetCurrentUserStreamUseCase,
) : BaseViewModel<EditProfileState, EditProfileSideEffect, EditProfileEvent>(EditProfileState()) {

    override fun onEvent(event: EditProfileEvent) {
        when (event) {
            EditProfileEvent.NavigateBackToProfile -> navigateToProfile()
            EditProfileEvent.ShowProfileEditSheet -> showProfileEditBottomSheet()
            EditProfileEvent.HideProfileEditSheet -> hideProfileEditBottomSheet()
            EditProfileEvent.OnLaunchGallery -> emitSideEffect(EditProfileSideEffect.LaunchGallery)
            is EditProfileEvent.OnPhotoSelected -> {
                updateState {
                    copy(selectedImageUri = event.imageUri)
                }
            }

            is EditProfileEvent.SavePhotoInStorage -> updateProfileImage(event.imageUri)
        }
    }

    init {
        observeCurrentUser()
    }

    private fun showProfileEditBottomSheet() {
        updateState { copy(selectedProfileEdit = true) }
    }

    private fun hideProfileEditBottomSheet() {
        updateState { copy(selectedProfileEdit = false) }
    }

    private fun navigateToProfile() {
        emitSideEffect(EditProfileSideEffect.NavigateToProfile)
    }

    private fun updateProfileImage(uri: Uri?) {
        viewModelScope.launch {
            val oldUrl = state.value.profileImageUrl.takeIf { it.isNotEmpty() }

            enqueueFileUploadUseCase(uri.toString())
                .onSuccess { newUrl ->
                    updateState {
                        copy(
                            profileImageUrl = newUrl,
                            selectedImageUri = null
                        )
                    }
                    updateProfilePictureUseCase(newUrl)

                    oldUrl?.let { deleteFileUseCase(it) }
                }
        }

    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            getCurrentUserStreamUseCase().collect { resource ->
                resource.onSuccess { userDomain ->
                    updateState { copy(user = userDomain?.toPresentation()) }
                }
            }
        }
    }
}