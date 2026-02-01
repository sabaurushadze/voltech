package com.tbc.profile.presentation.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.designsystem.components.radiobutton.VoltechRadioButtonDefaults
import com.tbc.core.designsystem.components.topbar.TopBarAction
import com.tbc.core.designsystem.components.topbar.TopBarState
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.profile.domain.model.settings.VoltechThemeOption
import com.tbc.profile.presentation.R
import com.tbc.profile.presentation.mapper.toStringRes

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetupTopBar(onSetupTopBar, viewModel::onEvent)

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingsSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            SettingsSideEffect.NavigateBackToProfile -> {
                navigateBack()
            }
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
    var showThemeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(VoltechColor.background)
            .fillMaxSize()
    ) {
        SettingsHeaderItem(title = stringResource(R.string.account))

        SettingsItem(
            text = stringResource(R.string.sign_out),
            onItemClick = { onEvent(SettingsEvent.SignOut) }
        )

        SettingsHeaderItem(title = stringResource(R.string.general))

        SettingsItem(
            text = stringResource(R.string.theme),
            onItemClick = { showThemeDialog = true }
        )
    }

    if (showThemeDialog) {
        ThemeDialog(
            currentTheme = state.themeOption,
            onDismiss = { showThemeDialog = false },
            onThemeSelected = { theme ->
                onEvent(SettingsEvent.ThemeChanged(theme))
                showThemeDialog = false
            }
        )
    }
}

@Composable
private fun ThemeDialog(
    currentTheme: VoltechThemeOption,
    onDismiss: () -> Unit,
    onThemeSelected: (VoltechThemeOption) -> Unit,
) {
    AlertDialog(
        containerColor = VoltechColor.surface,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.select_app_theme),
                color = VoltechColor.onSurface,
                style = VoltechTextStyle.body18Normal
            )
        },
        text = {
            Column {
                VoltechThemeOption.entries.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onThemeSelected(option) }
                            .padding(vertical = Dimen.size8),
                    ) {
                        RadioButton(
                            selected = option == currentTheme,
                            onClick = null,
                            colors = VoltechRadioButtonDefaults.primaryColors
                        )

                        Spacer(modifier = Modifier.width(Dimen.size8))

                        Text(
                            text = stringResource(option.toStringRes()),
                            color = VoltechColor.onSurface,
                            style = VoltechTextStyle.body20Bold,
                            modifier = Modifier.padding(start = Dimen.size8)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.cancel),
                color = VoltechColor.primary,
                style = VoltechTextStyle.body16Bold,
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(horizontal = Dimen.size16, vertical = Dimen.size8)
            )
        }
    )
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

@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    onEvent: (SettingsEvent) -> Unit,
) {
    val title = stringResource(id = R.string.settings)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = { onEvent(SettingsEvent.NavigateBackToProfile) }
                )
            )
        )
    }
}