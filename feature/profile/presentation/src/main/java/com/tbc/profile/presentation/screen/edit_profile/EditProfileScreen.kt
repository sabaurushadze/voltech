package com.tbc.profile.presentation.screen.edit_profile

import android.net.Uri
import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core.presentation.util.rememberGalleryLauncher
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.button.SecondaryButton
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.profile.presentation.components.ProfilePictureSheet
import com.tbc.resource.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigateBackToProfile: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val profilePictureBottomSheetState = rememberModalBottomSheetState()

    val launchGallery = rememberGalleryLauncher(
        onImageSelected = {
            d("EditProfile", "Selected uri: $it")
            viewModel.onEvent(EditProfileEvent.OnPhotoSelected(imageUri = it))
        }
    )

    SetupTopBar(onSetupTopBar, viewModel::onEvent)

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is EditProfileSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            EditProfileSideEffect.NavigateToProfile -> {
                navigateBackToProfile()
            }

            EditProfileSideEffect.LaunchGallery -> launchGallery()
        }
    }

    EditProfileContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

    LaunchedEffect(Unit) {
        d("asdd", "IMAGE URL: ${state.profileImageUrl}")
        d("asdd", "URI: ${state.selectedImageUri}")
    }
    if (state.selectedProfileEdit) {
        ModalBottomSheet(
            containerColor = VoltechColor.backgroundSecondary,
            onDismissRequest = { viewModel.onEvent(EditProfileEvent.HideProfileEditSheet) },
            sheetState = profilePictureBottomSheetState
        ) {
            ProfilePictureSheet(
                onChooseExistingPhotoClick = {
                    viewModel.onEvent(EditProfileEvent.OnLaunchGallery)
                    viewModel.onEvent(EditProfileEvent.HideProfileEditSheet)
                },
                onTakePhotoClick = {},
                onRemovePhotoClick = {}
            )
        }
    }

}

@Composable
private fun EditProfileContent(
    state: EditProfileState,
    onEvent: (EditProfileEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(VoltechColor.backgroundPrimary)
            .fillMaxSize()
    ) {
        state.user?.let { user ->
            UserProfileSection(
                imageUrl = user.photoUrl,
                userName = "luka",
                onProfileEditClick = { onEvent(EditProfileEvent.ShowProfileEditSheet) },
                selectedImageUri = state.selectedImageUri
            )
        }

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.size16),
            text = stringResource(R.string.save),
            onClick = { onEvent(EditProfileEvent.SavePhotoInStorage(state.selectedImageUri)) }
        )

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.size16),
            text = stringResource(R.string.cancel),
            onClick = { onEvent(EditProfileEvent.NavigateBackToProfile) }
        )


    }
}

@Composable
private fun UserProfileSection(
    imageUrl: String,
    userName: String,
    selectedImageUri: Uri?,
    onProfileEditClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(VoltechColor.backgroundElevated)
            .padding(vertical = Dimen.size16)
            .padding(start = Dimen.size16, end = Dimen.size16, top = Dimen.size4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier.size(Dimen.size64)
        ) {
            Box(
                modifier = Modifier
                    .size(Dimen.size64)
                    .clip(VoltechRadius.radius64)
                    .background(VoltechColor.foregroundAccent),
                contentAlignment = Alignment.Center
            ) {
                val imageToShow = selectedImageUri?.toString() ?: imageUrl

                BaseAsyncImage(
                    url = imageToShow, modifier = Modifier.clip(VoltechRadius.radius64)
                )

            }

            Box(
                modifier = Modifier
                    .size(Dimen.size32)
                    .offset(x = Dimen.size12, y = (-Dimen.size8))
                    .clip(VoltechRadius.radius64)
                    .background(VoltechColor.backgroundSecondary)
                    .clickable { onProfileEditClick() }
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_edit),
                    contentDescription = null,
                    tint = VoltechColor.foregroundPrimary,
                    modifier = Modifier.size(Dimen.size20)
                )
            }
        }


        Spacer(modifier = Modifier.width(Dimen.size24))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userName,
                color = VoltechColor.foregroundPrimary,
                style = VoltechTextStyle.body
            )

        }


    }
}

@Composable
private fun SectionHeader(
    title: String,
) {
    Row(
        modifier = Modifier
            .padding(
                start = Dimen.size16,
                end = Dimen.size16,
                top = Dimen.size16,
                bottom = Dimen.size8
            )
            .fillMaxWidth()
    ) {
        Text(
            text = title, color = VoltechColor.foregroundPrimary, style = VoltechTextStyle.title2
        )
    }
}

@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    onEvent: (EditProfileEvent) -> Unit,
) {
    val title = stringResource(id = R.string.user_details)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = R.drawable.ic_arrow_back,
                    onClick = { onEvent(EditProfileEvent.NavigateBackToProfile) }
                )
            )
        )
    }
}