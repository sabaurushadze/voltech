package com.tbc.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.auth.presentation.R
import com.tbc.designsystem.components.VoltechButton
import com.tbc.designsystem.theme.Black
import com.tbc.designsystem.theme.VoltechTheme
import com.tbc.designsystem.theme.White

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
    onShowSnackBar: (String) -> Unit,
    onSuccessfulAuth: () -> Unit = {},
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterContent(
        state = state,
        onEvent = viewModel::onEvent,
        onBackClick = { navigateBack() }
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                RegisterSideEffect.Success -> {
                    onSuccessfulAuth()
                }

                is RegisterSideEffect.ShowSnackBar -> {
                    val error = context.getString(sideEffect.errorRes)
                    onShowSnackBar(error)
                }
            }
        }
    }

}


@Composable
fun RegisterContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(
                    bottom = 32.dp
                ),
                text = stringResource(R.string.register),
                fontSize = 32.sp
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Black,
                    focusedTextColor = Black,
                    focusedLabelColor = Black,
                    focusedLeadingIconColor = Black,

                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.PasswordChanged(it)) },
                label = { Text(stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Black,
                    focusedTextColor = Black,
                    focusedLabelColor = Black,
                    focusedLeadingIconColor = Black,

                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            VoltechButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                text = stringResource(R.string.register),
                buttonColor = Black,
                textColor = White,
                textSize = 14.sp,
                onClick = {
                    onEvent(RegisterEvent.Register)
                }
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    VoltechTheme() {
        RegisterContent(
            state = RegisterState(
                isLoading = true,
                email = "123",
                password = "123"
            ),
            onEvent = {},
            onBackClick = {}
        )
    }
}