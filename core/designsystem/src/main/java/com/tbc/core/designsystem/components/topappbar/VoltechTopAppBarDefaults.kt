package com.tbc.core.designsystem.components.topappbar

import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.tbc.core.designsystem.theme.VoltechColor

object VoltechTopAppBarDefaults {
    val primaryColors: TopAppBarColors
        @Composable
        get() = TopAppBarDefaults.topAppBarColors(
            containerColor = VoltechColor.background,
            scrolledContainerColor = VoltechColor.background
        )
}