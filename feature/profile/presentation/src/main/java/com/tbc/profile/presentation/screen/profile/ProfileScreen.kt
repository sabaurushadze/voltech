package com.tbc.profile.presentation.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToUserDetails: () -> Unit,
    navigateToRecentlyViewed: () -> Unit,
    navigateToWatchlist: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetupTopBar(onSetupTopBar)

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProfileSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            ProfileSideEffect.NavigateToSettings -> {
                navigateToSettings()
            }

            ProfileSideEffect.NavigateToUserDetails -> {
                navigateToUserDetails()
            }

            ProfileSideEffect.NavigateToWatchlist -> {
                navigateToWatchlist()
            }
        }
    }

    ProfileContent(
        state = state,
        onEvent = viewModel::onEvent,
        navigateToRecentlyViewed = navigateToRecentlyViewed
    )

}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    navigateToRecentlyViewed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(VoltechColor.backgroundPrimary)
            .fillMaxSize()
    ) {
        UserProfileSection(
            imageUrl = state.user?.photoUrl,
            userName = state.user?.name,
            onUserProfileClick = { onEvent(ProfileEvent.NavigateToUserDetails) }
        )

        SectionHeader(title = "Shopping")

        IconTextSectionItem(
            title = "Saved",
            subTitle = "Searches",
            icon = R.drawable.ic_outlined_heart,
            onItemClick = { onEvent(ProfileEvent.NavigateToWatchlist) }
        )

        IconTextSectionItem(
            title = "Recently viewed",
            subTitle = "Listings you recently viewed",
            icon = R.drawable.ic_history,
            onItemClick = { navigateToRecentlyViewed() }
        )

        SectionHeader(title = "Account")

        TextSectionItem(
            text = stringResource(R.string.settings),
            onItemClick = { onEvent(ProfileEvent.NavigateToSettings) }
        )
    }
}


@Composable
private fun IconTextSectionItem(
    title: String,
    subTitle: String,
    @DrawableRes icon: Int,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(horizontal = Dimen.size16, vertical = Dimen.size8)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(VoltechRadius.radius64)
                .size(Dimen.size48)
                .background(VoltechColor.backgroundSecondary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(Dimen.size24),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = VoltechColor.foregroundPrimary
            )
        }

        Spacer(modifier = Modifier.width(Dimen.size16))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                color = VoltechColor.foregroundPrimary,
                style = VoltechTextStyle.body
            )

            Text(
                text = subTitle,
                color = VoltechColor.foregroundSecondary,
                style = VoltechTextStyle.caption
            )
        }
    }
}

@Composable
private fun TextSectionItem(
    text: String,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onItemClick
            )
            .padding(Dimen.size16)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = VoltechColor.foregroundPrimary,
            style = VoltechTextStyle.body
        )
    }
}

@Composable
private fun UserProfileSection(
    imageUrl: String?,
    userName: String?,
    onUserProfileClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onUserProfileClick() }
            .padding(start = Dimen.size16, end = Dimen.size16, top = Dimen.size4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier
                .size(Dimen.size64)
                .clip(VoltechRadius.radius64)
                .background(VoltechColor.foregroundAccent),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                BaseAsyncImage(
                    url = imageUrl, modifier = Modifier.clip(VoltechRadius.radius64)
                )
            } else {
                userName?.let {
                    Text(
                        text = if (userName.isNotEmpty()) userName.first().uppercase() else "",
                        color = VoltechColor.foregroundOnAccent,
                        style = VoltechTextStyle.title1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(Dimen.size16))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            userName?.let {
                Text(
                    text = userName,
                    color = VoltechColor.foregroundPrimary,
                    style = VoltechTextStyle.title3
                )
            }


            Icon(
                modifier = Modifier.size(Dimen.size32),
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = VoltechColor.foregroundPrimary
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
) {
    val title = stringResource(R.string.profile)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
            )
        )
    }
}