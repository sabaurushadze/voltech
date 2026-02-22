package com.tbc.voltech.presentation.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import com.tbc.core_ui.components.textfield.TextInputFieldDummy
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarConfig
import com.tbc.core_ui.theme.Dimen
import com.tbc.home.presentation.navigation.HomeScreenRoute
import com.tbc.profile.presentation.navigation.EditProfileScreenRoute
import com.tbc.profile.presentation.navigation.ProfileScreenRoute
import com.tbc.profile.presentation.navigation.RecentlyViewedScreenRoute
import com.tbc.profile.presentation.navigation.SettingsScreenRoute
import com.tbc.profile.presentation.navigation.WatchlistScreenRoute
import com.tbc.resource.R
import com.tbc.search.presentation.navigation.AddToCartScreenRoute
import com.tbc.search.presentation.navigation.FeedBackScreenRoute
import com.tbc.search.presentation.navigation.ItemDetailsScreenRoute
import com.tbc.search.presentation.navigation.SellerProfileScreenRoute
import com.tbc.selling.presentation.navigation.AddItemScreenRoute
import com.tbc.selling.presentation.navigation.MyItemsScreenRoute
import com.tbc.voltech.navigation.TopLevelDestination
import com.tbc.voltech.presentation.AppState
import com.tbc.voltech.presentation.isRouteInHierarchy

fun getTopBarConfig(
    currentDestination: NavDestination?,
    appState: AppState
): TopBarConfig? {
    return when {
        currentDestination.isRouteInHierarchy(FeedBackScreenRoute::class) ->
            TopBarConfig(
                title = R.string.feedback,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() },
                actions = listOf(
                    TopBarAction(
                        iconRes = R.drawable.ic_shopping_cart,
                        onClick = { appState.navController.navigate(AddToCartScreenRoute) }
                    )
                )
            )

        currentDestination.isRouteInHierarchy(SellerProfileScreenRoute::class) ->
            TopBarConfig(
                title = R.string.store,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() },
                actions = listOf(
                    TopBarAction(
                        iconRes = R.drawable.ic_shopping_cart,
                        onClick = { appState.navController.navigate(AddToCartScreenRoute) }
                    )
                )
            )

        currentDestination.isRouteInHierarchy(HomeScreenRoute::class) ->
            TopBarConfig(
                searchContent = {
                    TextInputFieldDummy(
                        modifier = Modifier
                            .padding(end = Dimen.size8, bottom = Dimen.size4)
                            .fillMaxWidth(),
                        label = stringResource(R.string.search_on_voltech),
                        startIcon = ImageVector.vectorResource(R.drawable.ic_search),
                        onClick = { appState.navigateToTopLevelDestination(TopLevelDestination.SEARCH) }
                    )
                },
                actions = listOf(
                    TopBarAction(
                        iconRes = R.drawable.ic_shopping_cart,
                        onClick = { appState.navController.navigate(AddToCartScreenRoute) }
                    )

                )
            )

        currentDestination.isRouteInHierarchy(ProfileScreenRoute::class) ->
            TopBarConfig(
                title = R.string.profile,
                actions = listOf(
                    TopBarAction(
                        iconRes = R.drawable.ic_shopping_cart,
                        onClick = { appState.navController.navigate(AddToCartScreenRoute) }
                    )
                )
            )

        currentDestination.isRouteInHierarchy(EditProfileScreenRoute::class) ->
            TopBarConfig(
                title = R.string.user_details,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        currentDestination.isRouteInHierarchy(WatchlistScreenRoute::class) ->
            TopBarConfig(
                title = R.string.watchlist,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        currentDestination.isRouteInHierarchy(RecentlyViewedScreenRoute::class) ->
            TopBarConfig(
                title = R.string.recently_viewed,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        currentDestination.isRouteInHierarchy(SettingsScreenRoute::class) ->
            TopBarConfig(
                title = R.string.settings,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        currentDestination.isRouteInHierarchy(ItemDetailsScreenRoute::class) ->
            TopBarConfig(
                title = R.string.item,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        currentDestination.isRouteInHierarchy(MyItemsScreenRoute::class) ->
            TopBarConfig(title = R.string.my_items)


        currentDestination.isRouteInHierarchy(AddItemScreenRoute::class) ->
            TopBarConfig(
                title = R.string.add_item,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        currentDestination.isRouteInHierarchy(AddToCartScreenRoute::class) ->
            TopBarConfig(
                title = R.string.voltech_shopping_cart,
                showBackButton = true,
                backButtonAction = { appState.navController.navigateUp() }
            )

        else -> null
    }
}
