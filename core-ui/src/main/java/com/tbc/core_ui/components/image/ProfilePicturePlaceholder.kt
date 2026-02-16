package com.tbc.core_ui.components.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle

@Composable
fun ProfilePicturePlaceholder(
    boxSize: Dp = Dimen.size48,
    text: String? = null,
    textSize: TextStyle = VoltechTextStyle.title2
) {
    Box(
        modifier = Modifier
            .size(boxSize)
            .clip(CircleShape)
            .background(VoltechColor.foregroundAccent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text?.firstOrNull()?.uppercase() ?: "",
            color = VoltechColor.foregroundOnAccent,
            style = textSize
        )
    }
}