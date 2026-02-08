package com.tbc.core_ui.components.button

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechButtonDefaults {

    val primaryColors: ButtonColors
        @Composable
        get() = ButtonDefaults.buttonColors(
            containerColor = VoltechColor.foregroundAccent,
            disabledContainerColor = VoltechColor.foregroundAccent.copy(alpha = 0.28f),
            disabledContentColor = VoltechColor.foregroundOnAccent.copy(alpha = 0.28f)
        )

    val secondaryColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
            disabledContentColor = VoltechColor.backgroundPrimary.copy(alpha = 0.18f)
        )

    val tertiaryColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
            disabledContainerColor = VoltechColor.backgroundTertiary,
        )

    val borderlessColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
        )
}