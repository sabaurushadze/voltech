package com.tbc.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tbc.profile.presentation.screen.edit_profile.EditProfileScreen
import com.tbc.profile.presentation.screen.profile.ProfileScreen
import com.tbc.profile.presentation.screen.recently_viewed.RecentlyViewedScreen
import com.tbc.profile.presentation.screen.settings.SettingsScreen
import com.tbc.profile.presentation.screen.watchlist.WatchlistScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileNavGraph(
    navigateToWatchlist: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToRecentlyViewed: () -> Unit,
    navigateToBack: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToAddToCart: () -> Unit,
) {

    navigation<ProfileNavGraphRoute>(startDestination = ProfileScreenRoute) {

        composable<RecentlyViewedScreenRoute>{
            RecentlyViewedScreen(
                navigateToBack = navigateToBack,
                navigateToItemDetails = navigateToItemDetails
            )
        }

        composable<ProfileScreenRoute> {
            ProfileScreen(
                navigateToSettings = navigateToSettings,
                navigateToWatchlist = navigateToWatchlist,
                navigateToUserDetails = navigateToEditProfile,
                navigateToRecentlyViewed = navigateToRecentlyViewed,
                navigateToAddToCart = navigateToAddToCart
            )

        }

        composable<EditProfileScreenRoute> {
            EditProfileScreen(
                navigateBackToProfile = navigateBack
            )

        }

        composable<SettingsScreenRoute> {
            SettingsScreen(
                navigateBack = navigateBack,
            )

        }

        composable<WatchlistScreenRoute> {
            WatchlistScreen(
                navigateBack = navigateBack,
                navigateToItemDetails = navigateToItemDetails
            )

        }

    }
}

@Serializable
data object RecentlyViewedScreenRoute

@Serializable
data object ProfileNavGraphRoute

@Serializable
data object ProfileScreenRoute

@Serializable
data object EditProfileScreenRoute

@Serializable
data object SettingsScreenRoute

@Serializable
data object WatchlistScreenRoute