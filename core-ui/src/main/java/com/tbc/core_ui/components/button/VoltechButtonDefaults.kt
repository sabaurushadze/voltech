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
            contentColor = VoltechColor.foregroundOnAccent,
            disabledContainerColor = VoltechColor.foregroundAccent.copy(alpha = 0.28f),
            disabledContentColor = VoltechColor.foregroundOnAccent.copy(alpha = 0.28f)
        )

    val secondaryColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
            containerColor = VoltechColor.backgroundPrimary,
            contentColor = VoltechColor.foregroundPrimary,
            disabledContainerColor = VoltechColor.backgroundPrimary.copy(alpha = 0.28f),
            disabledContentColor = VoltechColor.foregroundPrimary.copy(alpha = 0.28f)
        )

    val iconTextButtonColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
            containerColor = VoltechColor.backgroundPrimary,
            contentColor = VoltechColor.foregroundPrimary,
            disabledContainerColor = VoltechColor.backgroundPrimary.copy(alpha = 0.28f),
            disabledContentColor = VoltechColor.foregroundPrimary.copy(alpha = 0.28f)
        )
}