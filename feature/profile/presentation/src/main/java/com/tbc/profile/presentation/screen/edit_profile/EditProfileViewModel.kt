package com.tbc.profile.presentation.screen.edit_profile

import android.net.Uri
import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.profile.domain.usecase.edit_profile.DeleteFileUseCase
import com.tbc.profile.domain.usecase.edit_profile.EnqueueFileUploadUseCase
import com.tbc.profile.domain.usecase.edit_profile.UpdateProfilePictureUseCase
import com.tbc.profile.domain.usecase.edit_profile.UpdateUserNameUseCase
import com.tbc.profile.domain.usecase.edit_profile.ValidateUserNameUseCase
import com.tbc.profile.presentation.mapper.edit_profile.toPresentation
import com.tbc.resource.R
import com.tbc.selling.domain.model.SellerProfile
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.UpdateSellerProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val enqueueFileUploadUseCase: EnqueueFileUploadUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,

    private val getSellersUseCase: GetSellersUseCase,
    private val updateSellerProfileUseCase: UpdateSellerProfileUseCase,
) : BaseViewModel<EditProfileState, EditProfileSideEffect, EditProfileEvent>(EditProfileState()) {

    override fun onEvent(event: EditProfileEvent) {
        when (event) {
            EditProfileEvent.NavigateBackToProfile -> navigateToProfile()
            EditProfileEvent.ShowProfileEditSheet -> showProfileEditBottomSheet()
            EditProfileEvent.HideProfileEditSheet -> hideProfileEditBottomSheet()
            EditProfileEvent.OnLaunchGallery -> emitSideEffect(EditProfileSideEffect.LaunchGallery)
            is EditProfileEvent.OnPhotoSelected -> {
                updateState { copy(selectedImageUri = event.imageUri) }
            }

            is EditProfileEvent.SavePhotoInStorage -> updateProfileImage(event.imageUri)
            is EditProfileEvent.DeletePhotoFromStorage -> deleteProfileImage(event.imageUrl)
            is EditProfileEvent.UserNameChanged -> updateState { copy(userName = event.userName) }
            is EditProfileEvent.SaveUsername -> saveUsername(event.userName)
        }
    }

    init {
        getCurrentUser()
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

    private fun saveUsername(username: String) = viewModelScope.launch {
        updateState { copy(showUsernameError = false, isLoading = true) }

        val showUserNameError = validateUserNameUseCase(username)

        if (showUserNameError && state.value.userName?.isNotEmpty() == true) {
            updateUserNameUseCase(username)
                .onSuccess {
                    state.value.seller?.let { seller ->
                        val updatedSeller = SellerProfile(
                            sellerPhotoUrl = seller.sellerPhotoUrl,
                            id = seller.id,
                            sellerName = username
                        )

                        updateSellerProfileUseCase(updatedSeller)
                    }
                    getCurrentUser()
                    emitSideEffect(
                        EditProfileSideEffect.ShowSnackBar(R.string.username_updated)
                    )
                    updateState { copy(isLoading = false) }
                }
                .onFailure {
                    emitSideEffect(
                        EditProfileSideEffect.ShowSnackBar(R.string.username_update_failed)
                    )
                    updateState { copy(isLoading = false) }
                }
        } else {
            updateState { copy(showUsernameError = !showUserNameError, isLoading = false) }
        }


    }

    private fun deleteProfileImage(url: String?) {
        viewModelScope.launch {
            url?.let {
                deleteFileUseCase(url)
                    .onSuccess {
                        updateProfilePictureUseCase(null)

                        state.value.seller?.let { seller ->
                            val updatedSeller = SellerProfile(
                                sellerPhotoUrl = null,
                                id = seller.id,
                                sellerName = seller.sellerName
                            )

                            updateSellerProfileUseCase(updatedSeller)
                        }

                        emitSideEffect(
                            EditProfileSideEffect.ShowSnackBar(R.string.profile_picture_deleted)
                        )
                        getCurrentUser()
                    }
                    .onFailure {
                        emitSideEffect(
                            EditProfileSideEffect.ShowSnackBar(R.string.delete_failed)
                        )
                    }

            }

        }
    }

    private fun updateProfileImage(uri: Uri?) = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        state.value.selectedImageUri?.let {
            enqueueFileUploadUseCase(uri.toString())
                .onSuccess { newUrl ->
                    updateState { copy(selectedImageUri = null) }

                    updateProfilePictureUseCase(newUrl)
                        .onSuccess {
                            state.value.seller?.let { seller ->
                                val updatedSeller = SellerProfile(
                                    sellerPhotoUrl = newUrl,
                                    id = seller.id,
                                    sellerName = seller.sellerName
                                )

                                updateSellerProfileUseCase(updatedSeller)
                            }

                            emitSideEffect(
                                EditProfileSideEffect.ShowSnackBar(
                                    R.string.profile_picture_updated_successfully
                                )
                            )
                            updateState { copy(isLoading = false) }
                            getCurrentUser()
                        }
                }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase()?.toPresentation()
            updateState { copy(user = currentUser, userName = currentUser?.name) }
            getSeller()
        }
    }

    private fun getSeller() = viewModelScope.launch {
        state.value.user?.let { user ->
            getSellersUseCase(user.uid)
                .onSuccess { sellers ->
                    val seller = sellers.map { it.toPresentation() }.firstOrNull()

                    seller?.let {
                        updateState { copy(seller = seller) }
                    }
                }
                .onFailure {
                    d("asdd", "failure message getSeller() $it")
                }
        }


    }
}