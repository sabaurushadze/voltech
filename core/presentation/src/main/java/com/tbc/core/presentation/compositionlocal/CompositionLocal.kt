package com.tbc.core.presentation.compositionlocal

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.tbc.core.presentation.state.TopBarState

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}

val LocalTopBarState = compositionLocalOf<TopBarState> {
    error("LocalTopBarState not provided")
}