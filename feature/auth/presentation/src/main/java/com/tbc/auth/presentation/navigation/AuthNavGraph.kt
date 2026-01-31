package com.tbc.auth.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tbc.auth.presentation.screen.login.LogInScreen
import com.tbc.auth.presentation.screen.register.RegisterScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph(
    navigateToRegister: () -> Unit,
    navigateBack: () -> Unit,
    onSuccessfulAuth: () -> Unit,
) {

    navigation<AuthNavGraphRoute>(startDestination = LoginScreenRoute) {

        composable<LoginScreenRoute> {
            LogInScreen(
                navigateToRegister = navigateToRegister,
                onSuccessfulAuth = onSuccessfulAuth
            )

        }

        composable<RegisterScreenRoute> {
            RegisterScreen(
                navigateBack = navigateBack,
                onSuccessfulAuth = onSuccessfulAuth
            )
        }
    }


}

@Serializable
data object AuthNavGraphRoute

@Serializable
data object LoginScreenRoute

@Serializable
data object RegisterScreenRoute