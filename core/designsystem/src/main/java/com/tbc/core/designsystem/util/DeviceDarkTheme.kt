package com.tbc.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalDarkTheme = compositionLocalOf { false }

val isDeviceDarkTheme: Boolean
    @Composable
    @ReadOnlyComposable
    get() = LocalDarkTheme.current