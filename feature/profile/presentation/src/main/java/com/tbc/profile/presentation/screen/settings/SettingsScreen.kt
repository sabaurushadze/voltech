package com.tbc.profile.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.designsystem.components.topappbar.VoltechTopBar
import com.tbc.core.designsystem.components.topappbar.VoltechTopBarTitle
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingsSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            SettingsSideEffect.NavigateBackToProfile -> { navigateBack() }
        }
    }

    SettingsContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

}

@Composable
private fun SettingsContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(VoltechColor.background)
            .fillMaxSize()
    ) {
        VoltechTopBar(
            title = "Settings",
            showBackButton = true,
            onBackClick = { onEvent(SettingsEvent.NavigateBackToProfile) },
        )

        SettingsHeaderItem(title = "Account")

        SettingsItem(
            text = "Sign Out",
            onItemClick = { /* sign out logic */ }
        )

        SettingsHeaderItem(title = "General")

        SettingsItem(
            text = "Theme",
            onItemClick = { /* Theme logic */ }
        )
    }
}

@Composable
private fun SettingsHeaderItem(
    title: String,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = Dimen.size16, vertical = Dimen.size8)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = VoltechColor.primary,
            style = VoltechTextStyle.body16Normal
        )
    }
}

@Composable
private fun SettingsItem(
    text: String,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick() }
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
