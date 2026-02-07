package com.tbc.auth.presentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.resource.R
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.button.SecondaryButton
import com.tbc.core_ui.components.divider.Divider
import com.tbc.core_ui.components.textfield.PasswordTextField
import com.tbc.core_ui.components.textfield.OutlinedTextInputField
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.core_ui.theme.VoltechTheme

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),
    onSuccessfulAuth: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LogInContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                LogInSideEffect.Success -> {
                    onSuccessfulAuth()
                }

                is LogInSideEffect.ShowSnackBar -> {
                    val error = context.getString(sideEffect.errorRes)
                    snackbarHostState.showSnackbar(message = error)
                }

                LogInSideEffect.NavigateToRegister -> {
                    navigateToRegister()
                }
            }
        }
    }
}


@Composable
fun LogInContent(
    state: LogInState,
    onEvent: (LogInEvent) -> Unit,
) {
    val emailError = if (state.showEmailError) stringResource(R.string.enter_valid_email) else null
    val passwordError =
        if (state.showPasswordError) stringResource(R.string.empty_password_field) else null


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VoltechColor.backgroundPrimary)
            .padding(Dimen.size16)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = Dimen.size48),
                text = stringResource(R.string.log_in),
                color = VoltechColor.foregroundPrimary,
                style = VoltechTextStyle.display1
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimen.size48)
                        .align(Alignment.CenterHorizontally),
                    color = VoltechColor.foregroundAccent
                )
            }
            Spacer(modifier = Modifier.height(Dimen.size16))



            OutlinedTextInputField(
                value = state.email,
                onTextChanged = { onEvent(LogInEvent.EmailChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email),
                enabled = !state.isLoading,
                errorText = emailError,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(Dimen.size16))


            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                enabled = !state.isLoading,
                label = stringResource(R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                errorText = passwordError,
                imeAction = ImeAction.Done,
                onTextChanged = { onEvent(LogInEvent.PasswordChanged(it)) },
                onToggleTextVisibility = { onEvent(LogInEvent.PasswordVisibilityChanged) }
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.log_in),
                enabled = state.isLoginEnabled,
//                sha/pe = VoltechRadius.radius16,
                onClick = {
                    onEvent(LogInEvent.LogIn)
                },
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            Divider(
                text = stringResource(R.string.or),
                 dividerColor = VoltechColor.borderMedium
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.register),
                onClick = {
                    onEvent(LogInEvent.NavigateToRegister)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    VoltechTheme() {
        LogInContent(
            state = LogInState(
                isLoading = true,
                email = "123",
                password = "123"
            ),
            onEvent = {},
        )
    }
}