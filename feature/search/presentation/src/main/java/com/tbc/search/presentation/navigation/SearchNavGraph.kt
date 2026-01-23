package com.tbc.search.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.tbc.search.presentation.screen.feed.FeedScreen
import com.tbc.search.presentation.screen.search.SearchScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.searchNavGraph(
    onShowSnackBar: (String) -> Unit,
    navigateToFeed: (String) -> Unit,
) {

    navigation<SearchNavGraphRoute>(startDestination = SearchScreenRoute) {

        composable<SearchScreenRoute> {
            SearchScreen(
                onShowSnackBar = onShowSnackBar,
                navigateToFeed = navigateToFeed
            )
        }

        composable<FeedScreenRoute> { backStackEntry ->
            val feedRoute = backStackEntry.toRoute<FeedScreenRoute>()
            FeedScreen()
        }
    }


}

@Serializable
data object SearchNavGraphRoute

@Serializable
data object SearchScreenRoute

@Serializable
data object FeedScreenRoute