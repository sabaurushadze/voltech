package com.tbc.voltech.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.tbc.auth.presentation.navigation.RegisterScreenRoute
import com.tbc.auth.presentation.navigation.authNavGraph
import com.tbc.home.presentation.navigation.homeNavGraph
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.SearchNavGraphRoute
import com.tbc.search.presentation.navigation.SearchScreenRoute
import com.tbc.search.presentation.navigation.searchNavGraph
import com.tbc.voltech.presentation.AppState
import kotlin.reflect.KClass

@Composable
fun AppNavHost(
    appState: AppState,
    startDestination: KClass<*>,
    onShowSnackBar: (String) -> Unit,
    onSuccessfulAuth: () -> Unit,
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
            onShowSnackBar = onShowSnackBar,
            navigateToRegister = {
                navController.navigate(RegisterScreenRoute)
            },
            navigateBack = {
                navController.navigateUp()
            },
            onSuccessfulAuth = onSuccessfulAuth
        )

        homeNavGraph(
            onShowSnackBar = onShowSnackBar
        )
        
        searchNavGraph(
            onShowSnackBar = onShowSnackBar,
            navigateToFeed = { query ->
                navController.navigate(FeedScreenRoute(query = query))
            },
            navigateToSearch = {
                navController.navigateUp()
            }
        )


    }
}