package com.tbc.core.designsystem.components.button

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import com.tbc.core.designsystem.theme.VoltechColor

object VoltechButtonDefaults {

    val primaryColors: ButtonColors
        @Composable
        get() = ButtonDefaults.buttonColors(
            containerColor = VoltechColor.primary,
            contentColor = VoltechColor.onPrimary,
            disabledContainerColor = VoltechColor.primary.copy(alpha = 0.38f),
            disabledContentColor = VoltechColor.onPrimary.copy(alpha = 0.38f)
        )

    val secondaryColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
            containerColor = VoltechColor.background,
            contentColor = VoltechColor.onBackground,
            disabledContainerColor = VoltechColor.background.copy(alpha = 0.38f),
            disabledContentColor = VoltechColor.onBackground.copy(alpha = 0.38f)
        )

    val iconTextButtonColors: ButtonColors
        @Composable
        get() = ButtonDefaults.outlinedButtonColors(
            containerColor = VoltechColor.background,
            contentColor = VoltechColor.onBackground,
            disabledContainerColor = VoltechColor.background.copy(alpha = 0.38f),
            disabledContentColor = VoltechColor.onBackground.copy(alpha = 0.38f)
        )
}