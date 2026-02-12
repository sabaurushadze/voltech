package com.tbc.voltech.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import com.tbc.auth.presentation.navigation.RegisterScreenRoute
import com.tbc.auth.presentation.navigation.authNavGraph
//import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.home.presentation.navigation.homeNavGraph
import com.tbc.profile.presentation.navigation.EditProfileScreenRoute
import com.tbc.profile.presentation.navigation.RecentlyViewedScreenRoute
import com.tbc.profile.presentation.navigation.SettingsScreenRoute
import com.tbc.profile.presentation.navigation.WatchlistScreenRoute
import com.tbc.profile.presentation.navigation.profileNavGraph
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.ItemDetailsScreenRoute
import com.tbc.search.presentation.navigation.SearchScreenRoute
import com.tbc.search.presentation.navigation.searchNavGraph
import com.tbc.selling.presentation.navigation.AddItemScreenRoute
import com.tbc.selling.presentation.navigation.sellingNavGraph
import com.tbc.voltech.presentation.AppState
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    appState: AppState,
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
            navigateToFeed = { categoryQuery ->
                navController.navigate(FeedScreenRoute(categoryQuery = categoryQuery)) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            navigateToItemDetails = { recentlyItemId ->
                navController.navigate(ItemDetailsScreenRoute(id = recentlyItemId)) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            navigateToRecentlyViewed = {
                navController.navigate(RecentlyViewedScreenRoute){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )

        searchNavGraph(
            navigateToFeed = { query ->
                navController.navigate(FeedScreenRoute(query = query))
            },
            navigateToSearch = {
                navController.navigate(SearchScreenRoute)
            },
            navigateToItemDetails = { id ->
                navController.navigate(ItemDetailsScreenRoute(id))
            },
            navigateBack = { navController.navigateUp() },
        )

        profileNavGraph(
            navigateToSettings = { navController.navigate(SettingsScreenRoute) },
            navigateToWatchlist = { navController.navigate(WatchlistScreenRoute) },
            navigateBack = { navController.navigateUp() },
            navigateToEditProfile = { navController.navigate(EditProfileScreenRoute) },
            navigateToRecentlyViewed = { navController.navigate(RecentlyViewedScreenRoute) },
            navigateToBack = { navController.navigateUp() },
            navigateToItemDetails = { id ->
                navController.navigate(ItemDetailsScreenRoute(id))
            }
        )

        sellingNavGraph(
            navigateToAddItem = { navController.navigate(AddItemScreenRoute) },
            navigateBack = { navController.navigateUp() },
            navigateToItemDetails = { id ->
                navController.navigate(ItemDetailsScreenRoute(id))
            }
        )
    }
}