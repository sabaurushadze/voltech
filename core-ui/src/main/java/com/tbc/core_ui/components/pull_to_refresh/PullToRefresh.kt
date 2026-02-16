package com.tbc.core_ui.components.pull_to_refresh

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tbc.core_ui.theme.VoltechColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoltechPullToRefresh(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    onRefresh: () -> Unit,
    isRefreshing: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = {
            onRefresh()
        },
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
                isRefreshing = isRefreshing,
                color = VoltechColor.foregroundAccent,
                containerColor = VoltechColor.backgroundSecondary
            )
        },
        content = content
    )
}