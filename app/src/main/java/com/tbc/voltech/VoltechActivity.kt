package com.tbc.voltech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tbc.designsystem.theme.VoltechTheme
import com.tbc.presentation.login.LogInScreen
import com.tbc.presentation.navigation.AuthNavGraphRoute
import com.tbc.presentation.navigation.HomeScreenRoute
import com.tbc.voltech.main.MainActivityEvent
import com.tbc.voltech.main.MainViewModel
import com.tbc.voltech.presentation.VoltechApplication
import com.tbc.voltech.presentation.rememberAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.reflect.KClass

@AndroidEntryPoint
class VoltechActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { viewModel.state.value.isLoading }

        setContent {
            val state by viewModel.state.collectAsState()

            state.isAuthorized?.let { isAuthorized ->
                val startDestination =
                    if (isAuthorized) HomeScreenRoute::class else AuthNavGraphRoute::class
                val appState = rememberAppState()
                VoltechTheme() {
                    VoltechApplication(
                        appState = appState,
                        startDestination = startDestination,
                        onSuccessfulAuth = {
                            viewModel.onEvent(MainActivityEvent.OnSuccessfulAuth)
                        }
                    )
                }
            }
        }
    }
}

