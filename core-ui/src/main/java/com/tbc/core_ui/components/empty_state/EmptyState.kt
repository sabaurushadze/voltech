package com.tbc.core_ui.components.empty_state

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle

@Composable
fun EmptyState(
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.size48),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(Dimen.size80),
            imageVector = ImageVector.vectorResource(icon),
            tint = VoltechColor.backgroundTertiary,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

        Text(
            text = title,
            style = VoltechTextStyle.title2,
            color = VoltechColor.foregroundPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimen.size8))

        Text(
            text = subtitle,
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundSecondary,
            textAlign = TextAlign.Center
        )
    }
}