package com.tbc.voltech.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.tbc.auth.presentation.navigation.AuthNavGraphRoute
import com.tbc.auth.presentation.navigation.LoginScreenRoute
import com.tbc.auth.presentation.navigation.RegisterScreenRoute
import com.tbc.home.presentation.navigation.HomeScreenRoute
import com.tbc.profile.presentation.navigation.ProfileScreenRoute
import com.tbc.search.presentation.navigation.AddToCartScreenRoute
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.SearchScreenRoute
import com.tbc.selling.presentation.navigation.MyItemsScreenRoute
import com.tbc.voltech.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    return remember(navHostController, coroutineScope) {
        AppState(navHostController, coroutineScope)
    }
}

@Stable
data class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination.isRouteInHierarchy(topLevelDestination.route)
            }
        }

    val shouldShowBottomBar: Boolean
        @Composable get() {
            val destination = currentDestination ?: return false

            val hiddenRoutes = listOf(
                AuthNavGraphRoute::class,
                AddToCartScreenRoute::class
            )

            return hiddenRoutes.none { route ->
                destination.hasRoute(route)
            } && currentTopLevelDestination != null
        }

    val shouldShowTopBar: Boolean
        @Composable get() {
            val destination = currentDestination ?: return false

            val hiddenRoutes = listOf(
                LoginScreenRoute::class,
                RegisterScreenRoute::class,
                SearchScreenRoute::class,
                FeedScreenRoute::class,
            )

            return hiddenRoutes.none { route ->
                destination.hasRoute(route)
            }
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigate(HomeScreenRoute, topLevelNavOptions)
            TopLevelDestination.SEARCH -> navController.navigate(
                SearchScreenRoute,
                topLevelNavOptions
            )

            TopLevelDestination.PROFILE -> navController.navigate(
                ProfileScreenRoute,
                topLevelNavOptions
            )

            TopLevelDestination.SELLING -> navController.navigate(
                MyItemsScreenRoute,
                topLevelNavOptions
            )
        }
    }
}