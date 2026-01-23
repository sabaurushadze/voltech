package com.tbc.core.designsystem.components.textfield

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle

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
    keyboardType: KeyboardType = KeyboardType.Unspecified,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(),
        label = label?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body14Normal
                )
            }
        },
        textStyle = VoltechTextStyle.body16Normal,
        onValueChange = { onTextChanged(it) },
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = VoltechTextStyle.body14Normal,
                    color = VoltechColor.error
                )
            }
        },
        visualTransformation = visualTransformation,
        singleLine = true,
        isError = errorText != null,
        shape = VoltechRadius.radius16,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    )
}