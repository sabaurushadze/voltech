package com.tbc.core_ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    border: BorderStroke? = null,
    loading: Boolean = false,
    shape: RoundedCornerShape = VoltechRadius.radius16,

    enabled: Boolean = true,
) {
    BaseTextButton(
        onClick = onClick,
        text = text,
        shape = shape,
        border = BorderStroke(width = Dimen.size1, color = VoltechColor.borderAccent),
        modifier = modifier,
        startIcon = startIcon,
        endIcon = endIcon,
        loading = loading,
        enabled = enabled,
        textColor = VoltechColor.foregroundAccent,
        colors = VoltechButtonDefaults.secondaryColors
    )
}
