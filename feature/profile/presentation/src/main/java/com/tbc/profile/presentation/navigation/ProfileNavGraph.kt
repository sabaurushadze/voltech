package com.tbc.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.profile.presentation.screen.profile.ProfileScreen
import com.tbc.profile.presentation.screen.settings.SettingsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileNavGraph(
    navigateToSettings: () -> Unit,
    navigateBack: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,

    ) {

    navigation<ProfileNavGraphRoute>(startDestination = ProfileScreenRoute) {

        composable<ProfileScreenRoute> {
            ProfileScreen(
                navigateToSettings = navigateToSettings,
                onSetupTopBar = onSetupTopBar,
            )

        }

        composable<SettingsScreenRoute> {
            SettingsScreen(
                navigateBack = navigateBack,
                onSetupTopBar = onSetupTopBar
            )

        }

    }
}

@Serializable
data object ProfileNavGraphRoute

@Serializable
data object ProfileScreenRoute

@Serializable
data object SettingsScreenRoute