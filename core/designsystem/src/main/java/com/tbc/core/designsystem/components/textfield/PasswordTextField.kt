package com.tbc.core.designsystem.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle

@Composable
fun PasswordTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    onToggleTextVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    isPasswordVisible: Boolean = false,
    errorText: String? = null,
    shape: RoundedCornerShape = VoltechRadius.radius16,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.None,
) {
    val visualTransformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val endIcon = if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility

    OutlinedTextField(
        modifier = modifier.height(Dimen.size64),
        value = value,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = VoltechColor.onBackground,
            unfocusedBorderColor = VoltechColor.onBackground,
            focusedLabelColor = VoltechColor.primary,
            unfocusedLabelColor = VoltechColor.onBackground
        ),
        label = label?.let { { Text(text = it, style = VoltechTextStyle.body14Normal) } },
        textStyle = VoltechTextStyle.body16Normal,
        onValueChange = { onTextChanged(it) },
        visualTransformation = visualTransformation,
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body14Normal,
                    color = VoltechColor.error
                )
            }
        },
        singleLine = true,
        isError = errorText != null,
        shape = shape,
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