package com.tbc.profile.presentation.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.designsystem.components.topappbar.VoltechTopBar
import com.tbc.core.designsystem.components.topappbar.VoltechTopBarTitle
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.profile.presentation.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProfileSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            ProfileSideEffect.NavigateToSettings -> { navigateToSettings() }
        }
    }

    ProfileContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(VoltechColor.background)
            .fillMaxSize()
    ) {
        VoltechTopBar(title = "Profile")

        UserProfileSection(imageUrl = "", userName = "luka")

        SectionHeader(title = "Shopping")

        IconTextSectionItem(
            title = "Saved",
            subTitle = "Searches",
            icon = R.drawable.ic_outlined_heart,
            onItemClick = { /* aq daamate favoritebis scrini (profile screenes nestedshive iqneba route) */ }
        )

        IconTextSectionItem(
            title = "Recently viewed",
            subTitle = "Listings you recently viewed",
            icon = R.drawable.ic_history,
            onItemClick = { /* aq daamate recently viewed items scrini (profile screenes nestedshive iqneba route) */ }
        )

        SectionHeader(title = "Account")

        TextSectionItem(
            text = "Settings",
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
                .background(VoltechColor.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(Dimen.size24),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = VoltechColor.onSurface
            )
        }

        Spacer(modifier = Modifier.width(Dimen.size16))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                color = VoltechColor.onBackground,
                style = VoltechTextStyle.body16Medium
            )

            Text(
                text = subTitle,
                color = VoltechColor.onBackgroundSecondary,
                style = VoltechTextStyle.body12Medium
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
            color = VoltechColor.onBackground,
            style = VoltechTextStyle.body16Normal
        )
    }
}

@Composable
private fun UserProfileSection(
    imageUrl: String,
    userName: String,
) {
    Row(
        modifier = Modifier
            .padding(start = Dimen.size16, end = Dimen.size16, top = Dimen.size4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Box(
            modifier = Modifier
                .size(Dimen.size64)
                .clip(VoltechRadius.radius64)
                .background(VoltechColor.primary),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl.isEmpty()) {
                Text(
                    text = userName.first().toString().uppercase(),
                    color = VoltechColor.onPrimary,
                    style = VoltechTextStyle.body22Bold
                )
            } else {
                BaseAsyncImage(
                    url = imageUrl, modifier = Modifier.clip(VoltechRadius.radius64)
                )
            }
        }

        Spacer(modifier = Modifier.width(Dimen.size16))

        Text(
            text = userName, color = VoltechColor.onBackground, style = VoltechTextStyle.body16Bold
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
) {
    Row(
        modifier = Modifier
            .padding(start = Dimen.size16, end = Dimen.size16, top = Dimen.size16, bottom = Dimen.size8)
            .fillMaxWidth()
    ) {
        Text(
            text = title, color = VoltechColor.onBackground, style = VoltechTextStyle.body22Bold
        )
    }
}