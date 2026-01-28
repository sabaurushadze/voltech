package com.tbc.voltech.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.tbc.auth.presentation.navigation.RegisterScreenRoute
import com.tbc.auth.presentation.navigation.authNavGraph
import com.tbc.home.presentation.navigation.homeNavGraph
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.searchNavGraph
import com.tbc.voltech.presentation.AppState
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    appState: AppState,
    startDestination: KClass<*>,
    onSuccessfulAuth: () -> Unit,
    bottomAppBarScrollBehavior: BottomAppBarScrollBehavior,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
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
        )

        searchNavGraph(
            navigateToFeed = { query ->
                navController.navigate(FeedScreenRoute(query))
            },
            navigateToSearch = {
                navController.navigateUp()
            },
            bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
        )


    }
}