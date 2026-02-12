package com.tbc.voltech.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core_ui.components.topbar.TopBarContent
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.voltech.navigation.AppNavHost
import com.tbc.voltech.presentation.bottom_nav.VoltechBottomNavigation
import com.tbc.voltech.presentation.topbar.getTopBarConfig
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoltechApplication(
    appState: AppState,
    startDestination: KClass<*>,
    onSuccessfulAuth: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val currentDestination = appState.currentDestination
    val topLevelDestinations = appState.topLevelDestinations
    val shouldShowBottomBar = appState.shouldShowBottomBar
    val topBarConfig = getTopBarConfig(appState.currentDestination, appState)

    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarHostState,
    ) {
        Scaffold(
            containerColor = VoltechColor.backgroundPrimary,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                )
            },
            topBar = {
                topBarConfig?.let {
                    TopBarContent(topBarConfig)
                }
            },
            bottomBar = {
                VoltechBottomNavigation(
                    destinations = topLevelDestinations,
                    visible = shouldShowBottomBar,
                    currentDestination = currentDestination,
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
                )

            }

        }
    }

}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false