package com.tbc.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun VoltechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkVoltechColors
        else -> lightVoltechColors
    }

    CompositionLocalProvider(
        LocalColor provides colorScheme,
        LocalFixedColor provides fixedVoltechColors,
        LocalTypography provides AppTypography()

    ) {
        MaterialTheme(
            typography = Typography(),
            content = content
        )
    }
}