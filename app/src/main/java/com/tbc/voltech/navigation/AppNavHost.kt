package com.tbc.voltech.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import com.tbc.auth.presentation.navigation.RegisterScreenRoute
import com.tbc.auth.presentation.navigation.authNavGraph
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.home.presentation.navigation.homeNavGraph
import com.tbc.profile.presentation.navigation.EditProfileScreenRoute
import com.tbc.profile.presentation.navigation.SettingsScreenRoute
import com.tbc.profile.presentation.navigation.WatchlistScreenRoute
import com.tbc.profile.presentation.navigation.profileNavGraph
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.ItemDetailsRoute
import com.tbc.search.presentation.navigation.SearchScreenRoute
import com.tbc.search.presentation.navigation.searchNavGraph
import com.tbc.voltech.presentation.AppState
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    appState: AppState,
    onSetupAppBar: (TopBarState) -> Unit,
    startDestination: KClass<*>,
    onSuccessfulAuth: () -> Unit,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        authNavGraph(
            navigateToRegister = {
                navController.navigate(RegisterScreenRoute)
            },
            navigateBack = {
                navController.navigateUp()
            },
            onSuccessfulAuth = onSuccessfulAuth
        )

        homeNavGraph(
            onSetupTopBar = onSetupAppBar,
            navigateToSearch = { appState.navigateToTopLevelDestination(TopLevelDestination.SEARCH) },
            navigateToFeed = { categoryQuery ->
                navController.navigate(FeedScreenRoute(categoryQuery = categoryQuery)) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        searchNavGraph(
            navigateToFeed = { query ->
                navController.navigate(FeedScreenRoute(query = query))
            },
            navigateToSearch = {
                navController.navigate(SearchScreenRoute)
            },
            navigateToItemDetails = { id ->
                navController.navigate(ItemDetailsRoute(id))
            },
            navigateBack = { navController.navigateUp() },
            onSetupTopBar = onSetupAppBar
        )

        profileNavGraph(
            navigateToSettings = { navController.navigate(SettingsScreenRoute) },
            navigateToWatchlist = { navController.navigate(WatchlistScreenRoute) },
            navigateBack = { navController.navigateUp() },
            onSetupTopBar = onSetupAppBar,
            navigateToEditProfile = { navController.navigate(EditProfileScreenRoute) },
            navigateToItemDetails = { id ->
                navController.navigate(ItemDetailsRoute(id))
            }
        )


    }
}