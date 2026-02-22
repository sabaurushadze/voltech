package com.tbc.core_ui.theme

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

object VoltechNeutral {
    val N100: Color = Color(0xFFFFFFFF)
    val N200: Color = Color(0xFFF7F7F7)
    val N300: Color = Color(0xFFE5E5E5)
    val N400: Color = Color(0xFFC7C7C7)
    val N500: Color = Color(0xFF8F8F8F)
    val N600: Color = Color(0xFF707070)
    val N700: Color = Color(0xFF363636)
    val N800: Color = Color(0xFF191919)
    val N900: Color = Color(0xFF000000)
}


object VoltechBlue {
    val B100: Color = Color(0xFFF5F9FF)
    val B200: Color = Color(0xFFD4E5FE)
    val B300: Color = Color(0xFF84B4FB)
    val B400: Color = Color(0xFF4D93FC)
    val B500: Color = Color(0xFF0968F6)
    val B600: Color = Color(0xFF0049B8)
    val B700: Color = Color(0xFF002A69)
    val B800: Color = Color(0xFF19133A)
}

object VoltechGreen {
    val G100: Color = Color(0xFFF6FEF6)
    val G200: Color = Color(0xFFE0FAE0)
    val G300: Color = Color(0xFFA6F0A5)
    val G400: Color = Color(0xFF4CE160)
    val G500: Color = Color(0xFF3CC14E)
    val G600: Color = Color(0xFF288034)
    val G700: Color = Color(0xFF1B561A)
    val G800: Color = Color(0xFF0C310D)
}

object VoltechRed {
    val R100: Color = Color(0xFFFFF5F5)
    val R200: Color = Color(0xFFFFDEDE)
    val R300: Color = Color(0xFFFFA0A0)
    val R400: Color = Color(0xFFFF5C5C)
    val R500: Color = Color(0xFFF02D2D)
    val R600: Color = Color(0xFFD50B0B)
    val R700: Color = Color(0xFF570303)
    val R800: Color = Color(0xFF2A0303)
}

data class VoltechColors(
    val backgroundPrimary: Color = Color.Unspecified,
    val backgroundSecondary: Color = Color.Unspecified,
    val backgroundTertiary: Color = Color.Unspecified,
    val backgroundAccent: Color = Color.Unspecified,
    val backgroundAttention: Color = Color.Unspecified,
    val backgroundSuccess: Color = Color.Unspecified,
    val backgroundDisabled: Color = Color.Unspecified,
    val backgroundElevated: Color = Color.Unspecified,
    val backgroundSecondaryOnElevated: Color = Color.Unspecified,
    val backgroundOnSecondary: Color = Color.Unspecified,
    val backgroundInverse: Color = Color.Unspecified,
    val backgroundTransparent: Color = Color.Unspecified,


    val foregroundPrimary: Color = Color.Unspecified,
    val foregroundSecondary: Color = Color.Unspecified,
    val foregroundDisabled: Color = Color.Unspecified,
    val foregroundAccent: Color = Color.Unspecified,
    val foregroundAttention: Color = Color.Unspecified,
    val foregroundSuccess: Color = Color.Unspecified,
    val foregroundOnAccent: Color = Color.Unspecified,
    val foregroundOnAttention: Color = Color.Unspecified,
    val foregroundOnSuccess: Color = Color.Unspecified,
    val foregroundOnInverse: Color = Color.Unspecified,
    val foregroundOnDisabled: Color = Color.Unspecified,

    val borderStrong: Color = Color.Unspecified,
    val borderMedium: Color = Color.Unspecified,
    val borderSubtle: Color = Color.Unspecified,
    val borderAccent: Color = Color.Unspecified,
    val borderAttention: Color = Color.Unspecified,
    val borderSuccess: Color = Color.Unspecified,
    val borderOnAccent: Color = Color.Unspecified,
    val borderOnAttention: Color = Color.Unspecified,
    val borderOnSuccess: Color = Color.Unspecified,
    val borderOnInverse: Color = Color.Unspecified,
    val borderOnDisabled: Color = Color.Unspecified,
    val borderDisabled: Color = Color.Unspecified,
)

internal val lightVoltechColors = VoltechColors(
    backgroundPrimary = VoltechNeutral.N100,
    backgroundSecondary = VoltechNeutral.N200,
    backgroundTertiary = VoltechNeutral.N300,
    backgroundAccent = VoltechBlue.B500,
    backgroundAttention = VoltechRed.R600,
    backgroundSuccess = VoltechGreen.G600,
    backgroundDisabled = VoltechNeutral.N400,
    backgroundElevated = VoltechNeutral.N100,
    backgroundSecondaryOnElevated = VoltechNeutral.N200,
    backgroundOnSecondary = VoltechNeutral.N100,
    backgroundInverse = VoltechNeutral.N700,
    backgroundTransparent = VoltechNeutral.N100,

    foregroundPrimary = VoltechNeutral.N800,
    foregroundSecondary = VoltechNeutral.N600,
    foregroundDisabled = VoltechNeutral.N400,
    foregroundAccent = VoltechBlue.B500,
    foregroundAttention = VoltechRed.R600,
    foregroundSuccess = VoltechGreen.G600,
    foregroundOnAccent = VoltechNeutral.N100,
    foregroundOnAttention = VoltechNeutral.N100,
    foregroundOnSuccess = VoltechNeutral.N100,
    foregroundOnInverse = VoltechNeutral.N100,
    foregroundOnDisabled = VoltechNeutral.N100,

    borderStrong = VoltechNeutral.N700,
    borderMedium = VoltechNeutral.N500,
    borderSubtle = VoltechNeutral.N300,
    borderAccent = VoltechBlue.B500,
    borderAttention = VoltechRed.R600,
    borderSuccess = VoltechGreen.G600,
    borderOnAccent = VoltechNeutral.N100,
    borderOnAttention = VoltechNeutral.N100,
    borderOnSuccess = VoltechNeutral.N200,
    borderOnInverse = VoltechNeutral.N100,
    borderOnDisabled = VoltechNeutral.N100,
    borderDisabled = VoltechNeutral.N400,
)

internal val darkVoltechColors = VoltechColors(
    backgroundPrimary = VoltechNeutral.N900,
    backgroundSecondary = VoltechNeutral.N800,
    backgroundTertiary = VoltechNeutral.N700,
    backgroundAccent = VoltechBlue.B400,
    backgroundAttention = VoltechRed.R400,
    backgroundSuccess = VoltechGreen.G500,
    backgroundDisabled = VoltechNeutral.N600,
    backgroundElevated = VoltechNeutral.N800,
    backgroundSecondaryOnElevated = VoltechNeutral.N900,
    backgroundOnSecondary = VoltechNeutral.N900,
    backgroundInverse = VoltechNeutral.N300,
    backgroundTransparent = VoltechNeutral.N900,

    foregroundPrimary = VoltechNeutral.N200,
    foregroundSecondary = VoltechNeutral.N500,
    foregroundDisabled = VoltechNeutral.N600,
    foregroundAccent = VoltechBlue.B400,
    foregroundAttention = VoltechRed.R400,
    foregroundSuccess = VoltechGreen.G500,
    foregroundOnAccent = VoltechNeutral.N800,
    foregroundOnAttention = VoltechNeutral.N800,
    foregroundOnSuccess = VoltechNeutral.N800,
    foregroundOnInverse = VoltechNeutral.N800,
    foregroundOnDisabled = VoltechNeutral.N800,

    borderStrong = VoltechNeutral.N100,
    borderMedium = VoltechNeutral.N600,
    borderSubtle = VoltechNeutral.N700,
    borderAccent = VoltechBlue.B400,
    borderAttention = VoltechRed.R400,
    borderSuccess = VoltechGreen.G500,
    borderOnAccent = VoltechNeutral.N800,
    borderOnAttention = VoltechNeutral.N800,
    borderOnSuccess = VoltechNeutral.N800,
    borderOnInverse = VoltechNeutral.N800,
    borderOnDisabled = VoltechNeutral.N800,
    borderDisabled = VoltechNeutral.N700,
)

data class VoltechFixedColors(
    val black: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,
    val lightGray: Color = Color.Unspecified,
    val gray: Color = Color.Unspecified,
)

internal val fixedVoltechColors = VoltechFixedColors(
    black = Color(0xFF000000),
    white = Color(0xFFFFFFFF),
    lightGray = Color(0xFFF3F1F1),
    gray = Color(0xFF8F8F8F),
)
