package com.tbc.core.designsystem.components.radiobutton

import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import com.tbc.core.designsystem.theme.VoltechColor

object VoltechRadioButtonDefaults {

    val primaryColors: RadioButtonColors
        @Composable
        get() = RadioButtonDefaults.colors(
            selectedColor = VoltechColor.primary,
            unselectedColor = VoltechColor.onBackground
        )

}