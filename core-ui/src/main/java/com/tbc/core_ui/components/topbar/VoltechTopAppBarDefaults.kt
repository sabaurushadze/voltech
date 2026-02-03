package com.tbc.core_ui.components.topbar

import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechTopAppBarDefaults {
    val primaryColors: TopAppBarColors
        @Composable
        get() = TopAppBarDefaults.topAppBarColors(
            containerColor = VoltechColor.backgroundPrimary,
            scrolledContainerColor = VoltechColor.backgroundPrimary
        )
    val secondaryColors: TopAppBarColors
        @Composable
        get() = TopAppBarDefaults.topAppBarColors(
            containerColor = VoltechColor.backgroundElevated,
        )
}