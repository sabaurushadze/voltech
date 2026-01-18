package com.tbc.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tbc.presentation.login.LogInScreen
import com.tbc.presentation.register.RegisterScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph(
    onShowSnackBar: (String) -> Unit,
    navigateToRegister: () -> Unit,
    navigateBack: () -> Unit,
    onSuccessfulAuth: () -> Unit,
) {

    navigation<AuthNavGraphRoute>(startDestination = LoginScreenRoute) {

        composable<LoginScreenRoute> {
            LogInScreen(
                onShowSnackBar = onShowSnackBar,
                navigateToRegister = navigateToRegister,
                onSuccessfulAuth = onSuccessfulAuth
            )

        }

        composable<RegisterScreenRoute> {
            RegisterScreen(
                onShowSnackBar = onShowSnackBar,
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