package com.tbc.search.presentation.navigation

import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.tbc.search.presentation.screen.feed.FeedScreen
import com.tbc.search.presentation.screen.search.SearchScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchNavGraph(
    navigateToFeed: (String) -> Unit,
    navigateToSearch: () -> Unit,
    bottomAppBarScrollBehavior: BottomAppBarScrollBehavior,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    topAppBarPinnedScrollBehavior: TopAppBarScrollBehavior
) {

    navigation<SearchNavGraphRoute>(startDestination = SearchScreenRoute) {

        composable<SearchScreenRoute> {
            SearchScreen(
                navigateToFeed = navigateToFeed,
                topAppBarScrollBehavior = topAppBarPinnedScrollBehavior
            )
        }

        composable<FeedScreenRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<FeedScreenRoute>()

            FeedScreen(
                query = route.query,
                navigateToSearch = navigateToSearch,
                bottomAppBarScrollBehavior = bottomAppBarScrollBehavior,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
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