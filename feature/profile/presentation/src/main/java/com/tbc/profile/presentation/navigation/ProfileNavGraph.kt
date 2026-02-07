package com.tbc.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.profile.presentation.screen.edit_profile.EditProfileScreen
import com.tbc.profile.presentation.screen.profile.ProfileScreen
import com.tbc.profile.presentation.screen.settings.SettingsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileNavGraph(
    navigateToSettings: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEditProfile: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,

    ) {

    navigation<ProfileNavGraphRoute>(startDestination = ProfileScreenRoute) {

        composable<ProfileScreenRoute> {
            ProfileScreen(
                navigateToSettings = navigateToSettings,
                onSetupTopBar = onSetupTopBar,
                navigateToUserDetails = navigateToEditProfile,
            )

        }

        composable<EditProfileScreenRoute> {
            EditProfileScreen(
                onSetupTopBar = onSetupTopBar,
                navigateBackToProfile = navigateBack
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
data object EditProfileScreenRoute

@Serializable
data object SettingsScreenRoute