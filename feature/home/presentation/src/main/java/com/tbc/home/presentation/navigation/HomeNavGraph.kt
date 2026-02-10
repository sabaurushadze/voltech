package com.tbc.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.home.presentation.screen.home.HomeScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(
    navigateToSearch: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToFeed: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToRecentlyViewed: () -> Unit
) {

    composable<HomeScreenRoute> {
        HomeScreen(
            onSetupTopBar = onSetupTopBar,
            navigateToSearch = navigateToSearch,
            navigateToFeed = navigateToFeed,
            navigateToItemDetails = navigateToItemDetails,
            navigateToRecentlyViewed = navigateToRecentlyViewed,
        )
    }

}

@Serializable
data object HomeScreenRoute