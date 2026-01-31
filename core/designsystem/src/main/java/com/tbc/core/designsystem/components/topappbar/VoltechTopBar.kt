package com.tbc.core.designsystem.components.topappbar

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle

@Composable
fun VoltechTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(VoltechColor.background)
            .padding(
                start = if (showBackButton) Dimen.size0 else Dimen.size16,
                end = if (showBackButton) Dimen.size0 else Dimen.size16,
                top = if (showBackButton) Dimen.size0 else Dimen.size18,
                bottom = if (showBackButton) Dimen.size0 else Dimen.size18,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton && onBackClick != null) {
            VoltechTopBarBackButton(onBackClick = onBackClick)

            Spacer(modifier = Modifier.width(Dimen.size4))
        }

        Text(
            text = title,
            color = VoltechColor.onBackground,
            style = VoltechTextStyle.body22Bold
        )



    }
}

@Composable
fun VoltechTopBarBackButton(
    onBackClick: () -> Unit,
    clickableSize: Dp = Dimen.size64,
    rippleSize: Dp = Dimen.size36,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onBackClick
            )
            .size(clickableSize),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(VoltechRadius.radius64)
                .size(rippleSize)
                .indication(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = VoltechColor.onBackground
            )
        }
    }
}