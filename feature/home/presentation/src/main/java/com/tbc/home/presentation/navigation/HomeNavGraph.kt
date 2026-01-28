package com.tbc.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tbc.home.presentation.screen.HomeScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(
) {

    composable<HomeScreenRoute> {
        HomeScreen(
        )
    }

}

@Serializable
data object HomeScreenRoute