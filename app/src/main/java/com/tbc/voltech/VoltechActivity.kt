package com.tbc.voltech

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tbc.auth.presentation.navigation.AuthNavGraphRoute
import com.tbc.core_ui.theme.VoltechNeutral
import com.tbc.core_ui.theme.VoltechTheme
import com.tbc.home.presentation.navigation.HomeScreenRoute
import com.tbc.profile.domain.model.settings.VoltechThemeOption
import com.tbc.voltech.main.MainEvent
import com.tbc.voltech.main.MainViewModel
import com.tbc.voltech.presentation.VoltechApplication
import com.tbc.voltech.presentation.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoltechActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.state.value.isLoading }

        setContent {
            val state by viewModel.state.collectAsState()

            val showDarkTheme =
                ((state.themeOption == VoltechThemeOption.SYSTEM) && isSystemInDarkTheme()) || (state.themeOption == VoltechThemeOption.DARK)

            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = Color.TRANSPARENT,
                    darkScrim = Color.TRANSPARENT,
                ) { showDarkTheme },
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = VoltechNeutral.N100.toArgb(),
                    darkScrim = VoltechNeutral.N800.toArgb(),
                ) { showDarkTheme },
            )

            state.isAuthorized?.let { isAuthorized ->
                val startDestination =
                    if (isAuthorized) HomeScreenRoute::class else AuthNavGraphRoute::class
                val appState = rememberAppState()
                VoltechTheme(darkTheme = showDarkTheme) {
                    VoltechApplication(
                        appState = appState,
                        startDestination = startDestination,
                        onSuccessfulAuth = {
                            viewModel.onEvent(MainEvent.OnSuccessfulAuth)
                        },
                    )
                }
            }
        }
    }
}