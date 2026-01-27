package com.tbc.core.designsystem.components.checkbox

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import com.tbc.core.designsystem.theme.VoltechColor

object VoltechCheckBoxDefaults {
    val primaryColors: CheckboxColors
        @Composable
        get() = CheckboxDefaults.colors(
            checkedColor = VoltechColor.primary,
            uncheckedColor = VoltechColor.onBackground,
            checkmarkColor = VoltechColor.onPrimary,
            disabledCheckedColor = VoltechColor.onBackground,
            disabledUncheckedColor = VoltechColor.onBackground,
            disabledIndeterminateColor = VoltechColor.onBackground,
        )
}