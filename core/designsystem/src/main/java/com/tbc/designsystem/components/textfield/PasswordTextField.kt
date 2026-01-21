package com.tbc.designsystem.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.tbc.designsystem.theme.TextStyles
import com.tbc.designsystem.theme.VoltechColor
import com.tbc.designsystem.theme.VoltechRadius

@Composable
fun PasswordTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    onToggleTextVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    isPasswordVisible: Boolean = false,
    errorText: String? = null,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.None,
) {
    val visualTransformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val endIcon = if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility

    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(),
        label = label?.let { { Text(text = it, style = TextStyles.bodySmall) } },
        textStyle = TextStyles.bodyLarge,
        onValueChange = { onTextChanged(it) },
        visualTransformation = visualTransformation,
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = TextStyles.bodySmall,
                    color = VoltechColor.error
                )
            }
        },
        singleLine = true,
        isError = errorText != null,
        shape = VoltechRadius.radius16,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable(
                        enabled = enabled,
                        onClick = onToggleTextVisibility,
                        interactionSource = null,
                        indication = null
                    ),
                imageVector = endIcon, contentDescription = null
            )
        }

    )

}