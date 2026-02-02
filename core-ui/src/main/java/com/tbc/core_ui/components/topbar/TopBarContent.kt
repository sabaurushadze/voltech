package com.tbc.core_ui.components.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle

@Composable
@ExperimentalMaterial3Api
fun TopBarContent(topBarState: TopBarState) {
    TopAppBar(
        colors = VoltechTopAppBarDefaults.secondaryColors,
        title = {
            Text(
                text = topBarState.title,
                color = VoltechColor.foregroundPrimary,
                style = VoltechTextStyle.body22Bold
            )
        },
        navigationIcon = {
            topBarState.navigationIcon?.let { action ->
                IconButton(onClick = action.onClick) {
                    Icon(imageVector = action.icon, contentDescription = null)
                }
            }
        },
        actions = {
            topBarState.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(imageVector = action.icon, contentDescription = null)
                }
            }
        }
    )
}