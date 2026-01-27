package com.tbc.voltech.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.tbc.voltech.navigation.AppNavHost
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@Composable
fun VoltechApplication(
    appState: AppState,
    startDestination: KClass<*>,
    onSuccessfulAuth: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    VoltechApplication(
        appState = appState,
        startDestination = startDestination,
        snackbarHostState = snackbarHostState,
        onSuccessfulAuth = onSuccessfulAuth
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoltechApplication(
    appState: AppState,
    startDestination: KClass<*>,
    snackbarHostState: SnackbarHostState,
    onSuccessfulAuth: () -> Unit,
) {
    val coroutineScope = appState.coroutineScope

    val currentDestination = appState.currentDestination
    val topLevelDestinations = appState.topLevelDestinations
    val currentTopLevelDestination = appState.currentTopLevelDestination

    val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                hostState = snackbarHostState,
            )
        },
        bottomBar = {
            VoltechBottomNavigation(
                destinations = topLevelDestinations,
                visible = currentTopLevelDestination != null,
                currentDestination = currentDestination,
                bottomAppBarScrollBehavior = bottomAppBarScrollBehavior,
                onNavigateToDestination = { destination ->
                    appState.navigateToTopLevelDestination(destination)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
        ) {
            AppNavHost(
                appState = appState,
                startDestination = startDestination,
                onSuccessfulAuth = onSuccessfulAuth,
                onShowSnackBar = { message ->
                    if (snackbarHostState.currentSnackbarData == null)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message, withDismissAction = true)
                        }
                },
                bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
            )

        }

    }
}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false