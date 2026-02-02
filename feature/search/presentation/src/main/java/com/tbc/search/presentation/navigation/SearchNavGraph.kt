package com.tbc.search.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.search.presentation.screen.feed.FeedScreen
import com.tbc.search.presentation.screen.item_details.ItemDetailsScreen
import com.tbc.search.presentation.screen.search.SearchScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchNavGraph(
    navigateToFeed: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    navigateBack: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
) {

    navigation<SearchNavGraphRoute>(startDestination = SearchScreenRoute) {

        composable<SearchScreenRoute> {
            SearchScreen(
                navigateToFeed = navigateToFeed,
            )
        }

        composable<FeedScreenRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<FeedScreenRoute>()

            FeedScreen(
                query = route.query,
                navigateToSearch = navigateToSearch,
                navigateToItemDetails = navigateToItemDetails,
            )
        }

        composable<ItemDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ItemDetailsRoute>()

            ItemDetailsScreen(
                id = route.id,
                navigateBack = navigateBack,
                onSetupTopBar = onSetupTopBar,
            )
        }
    }


}

@Serializable
data object SearchNavGraphRoute

@Serializable
data object SearchScreenRoute

@Serializable
data class FeedScreenRoute(val query: String)

@Serializable
data class ItemDetailsRoute(val id: Int)