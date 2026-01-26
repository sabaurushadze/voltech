package com.tbc.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColor = compositionLocalOf { VoltechColors() }

val VoltechColor: VoltechColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColor.current

data class VoltechColors(
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,

    val neutral1: Color = Color.Unspecified,

    val neutralText1: Color = Color.Unspecified,

    val error: Color = Color.Unspecified,
    val unspecified: Color = Color.Unspecified,
    )

internal val lightVoltechColors = VoltechColors(
    primary = Color(0xFF0064D2),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFFF6F9FF),
    onBackground = Color(0xFF101010),

    neutral1 = Color(0xFFE0E0E0),

    neutralText1 = Color(0xFF626262),

    error = Color(0xFFB61616),
    unspecified = Color.Unspecified

)

internal val darkVoltechColors = VoltechColors(
    primary = Color(0xFF5A9BFF),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF2C2E36),
    onBackground = Color(0xFFF5F6FC),
    neutral1 = Color(0xFFE0E0E0),


    error = Color(0xFFEA5858),
    unspecified = Color.Unspecified
)