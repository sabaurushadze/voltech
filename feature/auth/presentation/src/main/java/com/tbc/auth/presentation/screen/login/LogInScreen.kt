package com.tbc.auth.presentation.screen.login

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.auth.presentation.R
import com.tbc.core.designsystem.components.button.PrimaryButton
import com.tbc.core.designsystem.components.button.SecondaryButton
import com.tbc.core.designsystem.components.divider.Divider
import com.tbc.core.designsystem.components.textfield.PasswordTextField
import com.tbc.core.designsystem.components.textfield.TextInputField
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.designsystem.theme.VoltechTheme

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),
    onSuccessfulAuth: () -> Unit,
    navigateToRegister: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val context = LocalResources.current
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
                    onShowSnackBar(error)
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
                color = VoltechColor.onBackground,
                style = VoltechTextStyle.title32Bold
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimen.size48)
                        .align(Alignment.CenterHorizontally),
                    color = VoltechColor.primary
                )
            }
            Spacer(modifier = Modifier.height(Dimen.size16))



            TextInputField(
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
                    .height(Dimen.buttonLarge)
                    .fillMaxWidth(),
                text = stringResource(R.string.log_in),
                enabled = state.isLoginEnabled,
                shape = VoltechRadius.radius16,
                onClick = {
                    onEvent(LogInEvent.LogIn)
                },
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            Divider(
                text = stringResource(R.string.or)
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.buttonLarge),
                text = stringResource(R.string.register),
                border = BorderStroke(
                    Dimen.size1, VoltechColor.onBackground
                ),
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