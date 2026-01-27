package com.tbc.core.designsystem.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle


@Composable
fun IconTextButton(
    onClick: () -> Unit,
    icon: ImageVector,
    @StringRes textRes: Int,
    border: BorderStroke? = null,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    BaseButton(
        onClick = onClick,
        modifier = modifier,
        border = border,
        loading = loading,
        enabled = enabled,
        colors = VoltechButtonDefaults.iconTextButtonColors
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = VoltechColor.primary,
                modifier = Modifier.padding(end = Dimen.size8)
            )

            Text(
                text = stringResource(textRes),
                style = VoltechTextStyle.body16Bold,
                color = VoltechColor.primary
            )
        }
    }
}