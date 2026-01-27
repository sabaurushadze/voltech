package com.tbc.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tbc.core.designsystem.theme.VoltechRadius

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    shape: RoundedCornerShape = VoltechRadius.radius16,
    content: @Composable () -> Unit,
) {
    val isEnabled = enabled && !loading

    if (border != null) {
        OutlinedButton(
            modifier = modifier,
            onClick = onClick,
            enabled = isEnabled,
            border = border,
            colors = colors,
            shape = shape
        ) {
            content()
        }
    } else {
        Button(
            modifier = modifier,
            onClick = onClick,
            enabled = isEnabled,
            border = border,
            colors = colors,
            shape = shape,
        ) {
            content()
        }
    }
}