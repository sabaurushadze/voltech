package com.tbc.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimen = compositionLocalOf { AppDimens() }

val Dimen: AppDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimen.current


data class AppDimens(
    val appPadding: Dp = 16.dp,

    val sizeExtraSmall: Dp = 2.dp,
    val sizeSmall: Dp = 4.dp,

    val size1: Dp = 1.dp,
    val size2: Dp = 2.dp,
    val size6: Dp = 6.dp,
    val size8: Dp = 8.dp,
    val size12: Dp = 12.dp,
    val size16: Dp = 16.dp,
    val size20: Dp = 20.dp,
    val size24: Dp = 24.dp,
    val size28: Dp = 28.dp,
    val size32: Dp = 32.dp,
    val size40: Dp = 40.dp,
    val size48: Dp = 48.dp,
    val size50: Dp = 50.dp,
    val size75: Dp = 75.dp,
    val size100: Dp = 100.dp,

    val roundedCornerMediumSize: Dp = 12.dp,

    val buttonLarge: Dp = 56.dp,
    val buttonMedium: Dp = 40.dp,
    val buttonSmall: Dp = 32.dp,

    val buttonPaddingInLarge: Dp = 12.dp,
    val buttonPaddingOutLarge: Dp = 24.dp,
    val buttonPaddingInMedium: Dp = 12.dp,
    val buttonPaddingOutMedium: Dp = 20.dp,
    val buttonPaddingInSmall: Dp = 8.dp,
    val buttonPaddingOutSmall: Dp = 12.dp,
    val iconLarge: Dp = 24.dp,
    val iconMedium: Dp = 20.dp,
    val iconSmall: Dp = 16.dp,


    )