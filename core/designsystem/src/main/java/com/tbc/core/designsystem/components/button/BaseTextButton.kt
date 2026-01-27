package com.tbc.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechRadius

@Composable
fun BaseTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: RoundedCornerShape = VoltechRadius.radius16,
    enabled: Boolean = true,
) {
    BaseButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        loading = loading,
        border = border,
        enabled = enabled,
        colors = colors
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            startIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(Dimen.size8)
                )
            }

            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            endIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(Dimen.size8)
                )
            }
        }
    }
}