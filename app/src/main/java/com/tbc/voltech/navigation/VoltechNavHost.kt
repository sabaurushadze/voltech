package com.tbc.voltech.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import com.tbc.auth.presentation.navigation.RegisterScreenRoute
import com.tbc.auth.presentation.navigation.authNavGraph
import com.tbc.home.presentation.navigation.homeNavGraph
import com.tbc.profile.presentation.navigation.EditProfileScreenRoute
import com.tbc.profile.presentation.navigation.RecentlyViewedScreenRoute
import com.tbc.profile.presentation.navigation.SettingsScreenRoute
import com.tbc.profile.presentation.navigation.WatchlistScreenRoute
import com.tbc.profile.presentation.navigation.profileNavGraph
import com.tbc.search.presentation.navigation.AddToCartScreenRoute
import com.tbc.search.presentation.navigation.FeedBackScreenRoute
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.ItemDetailsScreenRoute
import com.tbc.search.presentation.navigation.SearchScreenRoute
import com.tbc.search.presentation.navigation.SellerProfileScreenRoute
import com.tbc.search.presentation.navigation.searchNavGraph
import com.tbc.selling.presentation.navigation.AddItemScreenRoute
import com.tbc.selling.presentation.navigation.sellingNavGraph
import com.tbc.voltech.presentation.AppState
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoltechNavHost(
    appState: AppState,
    startDestination: KClass<*>,
    onSuccessfulAuth: () -> Unit,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        }
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
                navController.navigate(RecentlyViewedScreenRoute) {
                    popUpTo(navController.graph.findStartDestination().id) {
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
                navController.navigate(SearchScreenRoute) {
                    popUpTo(SearchScreenRoute) { inclusive = true }
                }
            },
            navigateToItemDetails = { id ->
                navController.navigate(ItemDetailsScreenRoute(id))
            },
            navigateBack = { navController.navigateUp() },
            navigateToAddToCart = {
                navController.navigate(AddToCartScreenRoute) {
                    popUpTo(AddToCartScreenRoute) { inclusive = true }
                }
            },

            navigateToSellerProfile = { sellerUid ->
                navController.navigate(SellerProfileScreenRoute(sellerUid)) {
                    popUpTo(SellerProfileScreenRoute(sellerUid)) { inclusive = true }
                }
            },
            navigateToFeedWithUid = { sellerUid ->
                navController.navigate(FeedScreenRoute(sellerUid = sellerUid))
            },
            navigateToFeedback = { sellerUid ->
                navController.navigate(FeedBackScreenRoute(sellerUid))
            },
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
            },
            navigateToAddToCart = { navController.navigate(AddToCartScreenRoute) }

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