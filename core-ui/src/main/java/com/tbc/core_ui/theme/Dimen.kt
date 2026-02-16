package com.tbc.core_ui.theme

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
    val size0: Dp = 0.dp,
    val size2: Dp = 2.dp,
    val size4: Dp = 4.dp,
    val size6: Dp = 6.dp,
    val size8: Dp = 8.dp,
    val size10: Dp = 10.dp,
    val size12: Dp = 12.dp,
    val size16: Dp = 16.dp,
    val size18: Dp = 18.dp,
    val size20: Dp = 20.dp,
    val size24: Dp = 24.dp,
    val size32: Dp = 32.dp,
    val size40: Dp = 40.dp,
    val size48: Dp = 48.dp,
    val size56: Dp = 56.dp,
    val size64: Dp = 64.dp,
    val size80: Dp = 80.dp,
    val size90: Dp = 90.dp,
    val size98: Dp = 98.dp,
    val size100: Dp = 100.dp,
    val size132: Dp = 132.dp,
    val size150: Dp = 150.dp,
    val size186: Dp = 186.dp,
    val size200: Dp = 200.dp,
    val size216: Dp = 216.dp,
    val size300: Dp = 300.dp,
    val size350: Dp = 350.dp,
    val size400: Dp = 400.dp
    )

