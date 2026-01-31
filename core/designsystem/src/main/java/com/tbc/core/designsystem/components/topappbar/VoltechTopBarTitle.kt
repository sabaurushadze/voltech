package com.tbc.core.designsystem.components.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle

@Composable
fun VoltechTopBarTitle(
    title: String,

) {
    Row(
        modifier = Modifier
            .background(VoltechColor.background)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(Dimen.size16),
            text = title,
            color = VoltechColor.onBackground,
            style = VoltechTextStyle.body24Bold
        )
    }
}