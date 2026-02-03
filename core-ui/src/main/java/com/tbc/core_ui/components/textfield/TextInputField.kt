package com.tbc.core_ui.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle


@Composable
fun TextInputField(
    value: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.None,
    shape: RoundedCornerShape = VoltechRadius.radius16,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    startIcon: ImageVector? = null,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = VoltechColor.foregroundPrimary,
            unfocusedBorderColor = VoltechColor.foregroundPrimary,
            focusedLabelColor = VoltechColor.foregroundAccent,
            unfocusedLabelColor = VoltechColor.foregroundPrimary,
            focusedTextColor = VoltechColor.foregroundPrimary,
            unfocusedTextColor = VoltechColor.foregroundPrimary
        ),
        label = label?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body,
                    color = VoltechColor.foregroundPrimary
                )
            }
        },
        textStyle = VoltechTextStyle.body,
        onValueChange = { onTextChanged(it) },
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body,
                    color = VoltechColor.foregroundAttention
                )
            }
        },
        visualTransformation = visualTransformation,
        singleLine = true,
        isError = errorText != null,
        shape = shape,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        leadingIcon = startIcon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = VoltechColor.foregroundPrimary
                )
            }
        }
    )
}

@Composable
fun TextInputFieldDummy(
    modifier: Modifier = Modifier,
    value: String = "",
    onTextChanged: (String) -> Unit = {},
    label: String? = null,
    enabled: Boolean = false,
    shape: RoundedCornerShape = VoltechRadius.radius16,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClick: () -> Unit = {},
    startIcon: ImageVector? = null,
) {
    OutlinedTextField(
        readOnly = true,
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .height(Dimen.size64),
        value = value,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = VoltechColor.foregroundPrimary,
            unfocusedTextColor = VoltechColor.foregroundPrimary,
        ),
        label = label?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body,
                    color = VoltechColor.foregroundPrimary
                )
            }
        },
        textStyle = VoltechTextStyle.body,
        onValueChange = { onTextChanged(it) },
        visualTransformation = visualTransformation,
        singleLine = true,
        shape = shape,
        leadingIcon = startIcon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = VoltechColor.foregroundPrimary
                )
            }
        }
    )
}