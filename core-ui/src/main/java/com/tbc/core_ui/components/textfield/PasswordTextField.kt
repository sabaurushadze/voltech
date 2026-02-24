package com.tbc.core_ui.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle

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
        modifier = modifier,
        value = value,
        enabled = enabled,
        colors = VoltechOutlinedTextFieldDefaults.primaryColors,
        label = label?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body,
                )
            }
        },
        textStyle = VoltechTextStyle.body,
        onValueChange = { onTextChanged(it) },
        visualTransformation = visualTransformation,
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body,
                    color = VoltechColor.foregroundAttention
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
                imageVector = endIcon,
                contentDescription = null,
                tint = VoltechColor.foregroundPrimary
            )
        }

    )

}