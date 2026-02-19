package com.tbc.search.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.tbc.search.presentation.screen.add_to_cart.AddToCartScreen
import com.tbc.search.presentation.screen.feed.FeedScreen
import com.tbc.search.presentation.screen.item_details.ItemDetailsScreen
import com.tbc.search.presentation.screen.search.SearchScreen
import com.tbc.search.presentation.screen.seler_profile.SellerProfileScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchNavGraph(
    navigateToFeed: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    navigateBack: () -> Unit,
    navigateToAddToCart: () -> Unit,
    navigateToSellerProfile: (String) -> Unit,
    navigateToFeedWithUid: (String) -> Unit
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
                categoryQuery = route.categoryQuery,
                sellerUid = route.sellerUid,
                navigateToSearch = navigateToSearch,
                navigateToItemDetails = navigateToItemDetails,
            )
        }

        composable<AddToCartScreenRoute> {
            AddToCartScreen(
                navigateToItemDetails = navigateToItemDetails
            )
        }

        composable<ItemDetailsScreenRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ItemDetailsScreenRoute>()

            ItemDetailsScreen(
                id = route.id,
                navigateBack = navigateBack,
                navigateToAddToCart = navigateToAddToCart,
                navigateToSellerProfile = navigateToSellerProfile,
            )
        }

        composable<SellerProfileScreenRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<SellerProfileScreenRoute>()
            SellerProfileScreen(
                sellerUid = route.sellerUid,
                navigateToFeedWithUid = navigateToFeedWithUid,
            )
        }
    }
}
@Serializable
data class SellerProfileScreenRoute(val sellerUid: String)

@Serializable
data object SearchNavGraphRoute

@Serializable
data object AddToCartScreenRoute

@Serializable
data object SearchScreenRoute

@Serializable
data class FeedScreenRoute(
    val query: String? = null,
    val categoryQuery: String? = null,
    val sellerUid: String? = null,
)

@Serializable
data class ItemDetailsScreenRoute(val id: Int)