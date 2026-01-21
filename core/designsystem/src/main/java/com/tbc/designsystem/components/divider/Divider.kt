package com.tbc.designsystem.components.divider

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tbc.designsystem.theme.Dimen
import com.tbc.designsystem.theme.TextStyles
import com.tbc.designsystem.theme.VoltechColor

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    text: String? = null,
    textColor: Color = VoltechColor.onBackground,
    dividerColor: Color = VoltechColor.onBackground,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            color = dividerColor
        )
        text?.let {
            Text(
                modifier = Modifier.padding(horizontal = Dimen.size24),
                text = it,
                style = TextStyles.labelMedium,
                color = textColor
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                color = dividerColor
            )
        }
    }
}