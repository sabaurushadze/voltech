package com.tbc.core.presentation.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class TopBarState {
    var topBarContent by mutableStateOf<(@Composable () -> Unit)?>(null)
        private set

    var scrollBehavior by mutableStateOf<TopAppBarScrollBehavior?>(null)
        private set

    private var ownerKey by mutableStateOf<String?>(null)

    fun setTopBar(
        content: @Composable () -> Unit,
        behavior: TopAppBarScrollBehavior? = null,
        key: String,
    ) {
        topBarContent = content
        scrollBehavior = behavior
        ownerKey = key
    }

    fun clearTopBar(key: String) {
        if (ownerKey == key) {
            topBarContent = null
            scrollBehavior = null
            ownerKey = null
        }
    }
}