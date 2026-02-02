package com.tbc.core_ui.components.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    loading: Boolean = false,
    shape: RoundedCornerShape = VoltechRadius.radius16,

    enabled: Boolean = true,
) {
    BaseTextButton(
        onClick = onClick,
        text = text,
        shape = shape,
        modifier = modifier,
        textColor = if (enabled) VoltechColor.foregroundOnAccent else VoltechColor.foregroundAccent,
        startIcon = startIcon,
        endIcon = endIcon,
        loading = loading,
        enabled = enabled,
        colors = VoltechButtonDefaults.primaryColors
    )
}
