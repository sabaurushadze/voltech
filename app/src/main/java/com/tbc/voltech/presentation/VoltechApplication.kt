package com.tbc.voltech.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

@Composable
fun VoltechApplication(
    appState: AppState,
    startDestination: KClass<*>,
    snackbarHostState: SnackbarHostState,
    onSuccessfulAuth: () -> Unit,
) {
    val coroutineScope = appState.coroutineScope

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                hostState = snackbarHostState,
            )
        },
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
                })
        }

    }
}