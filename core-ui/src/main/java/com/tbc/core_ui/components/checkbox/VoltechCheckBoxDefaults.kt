package com.tbc.core_ui.components.checkbox

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechCheckBoxDefaults {
    val primaryColors: CheckboxColors
        @Composable
        get() = CheckboxDefaults.colors(
            checkedColor = VoltechColor.foregroundAccent,
            uncheckedColor = VoltechColor.foregroundPrimary,
            checkmarkColor = VoltechColor.foregroundOnAccent,
            disabledCheckedColor = VoltechColor.foregroundPrimary,
            disabledUncheckedColor = VoltechColor.foregroundPrimary,
            disabledIndeterminateColor = VoltechColor.foregroundPrimary,
        )
}