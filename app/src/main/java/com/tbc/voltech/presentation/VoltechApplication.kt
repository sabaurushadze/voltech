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
import com.tbc.voltech.main.MainEvent
import com.tbc.voltech.main.MainState
import com.tbc.voltech.navigation.VoltechNavHost
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoltechApplication(
    mainState: MainState,
    onEvent: (MainEvent) -> Unit,
    appState: AppState,
    startDestination: KClass<*>,
    onSuccessfulAuth: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val currentDestination = appState.currentDestination
    val topLevelDestinations = appState.topLevelDestinations
    val shouldShowBottomBar = appState.shouldShowBottomBar

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
                if (appState.shouldShowTopBar) {
                    TopBarContent(mainState.topBarState)
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
                VoltechNavHost(
                    appState = appState,
                    startDestination = startDestination,
                    onSuccessfulAuth = onSuccessfulAuth,
                    onSetupAppBar = { onEvent(MainEvent.OnUpdateTopBarState(it)) }
                )

            }

        }
    }

}

fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false