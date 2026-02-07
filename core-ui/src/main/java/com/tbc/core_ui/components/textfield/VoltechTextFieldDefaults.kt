package com.tbc.core_ui.components.textfield

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechTextFieldDefaults {

    val primaryColors: TextFieldColors
        @Composable
        get() = TextFieldDefaults.colors(
            focusedContainerColor = VoltechColor.backgroundTertiary,
            unfocusedContainerColor = VoltechColor.backgroundTertiary,
            focusedIndicatorColor = VoltechColor.backgroundTertiary,
            unfocusedIndicatorColor = VoltechColor.backgroundTertiary,
            disabledIndicatorColor = VoltechColor.backgroundTertiary,
        )
}