package com.tbc.core_ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalDarkTheme = compositionLocalOf { false }

val isDeviceDarkTheme: Boolean
    @Composable
    @ReadOnlyComposable
    get() = LocalDarkTheme.current