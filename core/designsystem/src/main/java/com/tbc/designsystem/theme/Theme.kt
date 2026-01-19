package com.tbc.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Blue200,
    onPrimary = Black,

    primaryContainer = Blue700,
    onPrimaryContainer = White,

    secondary = Green200,
    onSecondary = Black,

    error = Red200,
    onError = Black,

    background = Gray700,
    onBackground = White,

    surface = Gray700,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = White,

    primaryContainer = Blue200,
    onPrimaryContainer = Black,

    secondary = Green500,
    onSecondary = White,

    error = Red500,
    onError = White,

    background = White,
    onBackground = Black,

    surface = Gray200,
    onSurface = Black
)

@Composable
fun VoltechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}