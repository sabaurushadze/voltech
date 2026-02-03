package com.tbc.core_ui.components.radiobutton

import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechRadioButtonDefaults {

    val primaryColors: RadioButtonColors
        @Composable
        get() = RadioButtonDefaults.colors(
            selectedColor = VoltechColor.foregroundAccent,
            unselectedColor = VoltechColor.foregroundPrimary
        )

}