package com.tbc.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.home.presentation.screen.HomeScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(
    onSetupTopBar: (TopBarState) -> Unit,
) {

    composable<HomeScreenRoute> {
        HomeScreen(
            onSetupTopBar = onSetupTopBar
        )
    }

}

@Serializable
data object HomeScreenRoute