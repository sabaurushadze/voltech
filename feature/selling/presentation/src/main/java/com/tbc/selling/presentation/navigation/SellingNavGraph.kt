package com.tbc.selling.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tbc.selling.presentation.screen.add_item.AddItemScreen
import com.tbc.selling.presentation.screen.my_items.MyItemsScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.sellingNavGraph(
    navigateToAddItem: () -> Unit,
    navigateBack: () -> Unit,
    navigateToItemDetails: (Int) -> Unit
) {

    navigation<SellingNavGraphRoute>(startDestination = MyItemsScreenRoute) {

        composable<MyItemsScreenRoute> {
            MyItemsScreen(
                navigateToAddItem = navigateToAddItem,
                navigateToItemDetails = navigateToItemDetails
            )
        }

        composable<AddItemScreenRoute> {
            AddItemScreen(
                navigateBackToMyItems = navigateBack
            )
        }


    }


}

@Serializable
data object SellingNavGraphRoute

@Serializable
data object MyItemsScreenRoute

@Serializable
data object AddItemScreenRoute
