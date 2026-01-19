package com.tbc.designsystem.components.textfield

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.tbc.designsystem.theme.Dimen
import com.tbc.designsystem.theme.TextStyles
import com.tbc.designsystem.theme.Transparent

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
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Transparent,
            errorContainerColor = Transparent,
            disabledContainerColor = Transparent,
            focusedContainerColor = Transparent,
        ),
        label = label?.let {
            {
                Text(
                    text = it,
                    style = TextStyles.bodySmall
                )
            }
        },
        textStyle = TextStyles.bodyLarge,
        onValueChange = { onTextChanged(it) },
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = TextStyles.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        visualTransformation = visualTransformation,
        singleLine = true,
        isError = errorText != null,
        shape = RoundedCornerShape(Dimen.roundedCornerMediumSize),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    )
}