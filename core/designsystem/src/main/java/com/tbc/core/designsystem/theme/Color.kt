package com.tbc.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColor = compositionLocalOf { VoltechColors() }
val LocalFixedColor = compositionLocalOf { VoltechFixedColors() }

val VoltechColor: VoltechColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColor.current

val VoltechFixedColor: VoltechFixedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalFixedColor.current

data class VoltechColors(
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,

    val neutral1: Color = Color.Unspecified,

    val neutralText1: Color = Color.Unspecified,

    val error: Color = Color.Unspecified,
    val unspecified: Color = Color.Unspecified,
    )

data class VoltechFixedColors(
    val black: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val transparent: Color = Color.Unspecified,
    val lightGray: Color = Color.Unspecified,
    val blue: Color = Color.Unspecified,
)

internal val lightVoltechColors = VoltechColors(
    primary = Color(0xFF0064D2),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF101010),
    surface = Color(0xFFF5F5F5),

    neutral1 = Color(0xFFE0E0E0),

    neutralText1 = Color(0xFF626262),

    error = Color(0xFFB61616),
    unspecified = Color.Unspecified

)

internal val darkVoltechColors = VoltechColors(
    primary = Color(0xFF5A9BFF),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF2C2E36),
    onBackground = Color(0xFFFFFFFF),
    neutral1 = Color(0xFFE0E0E0),
    surface = Color(0xFF262626),

    neutralText1 = Color(0xFF939292),

    error = Color(0xFFEA5858),
    unspecified = Color.Unspecified
)

internal val fixedVoltechColors = VoltechFixedColors(
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    transparent = Color(0x00FFFFFF),
    lightGray = Color(0xFFF3F1F1),
    blue = Color(0xFF0a4bff),
)
