package com.tbc.core_ui.components.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle

@Composable
@ExperimentalMaterial3Api
fun TopBarContent(topBarState: TopBarState) {
    TopAppBar(
        colors = VoltechTopAppBarDefaults.secondaryColors,
        title = {
            when {
                topBarState.titleContent != null -> {
                    topBarState.titleContent.invoke()
                }
                topBarState.title != null -> {
                    Text(
                        text = topBarState.title,
                        color = VoltechColor.foregroundPrimary,
                        style = VoltechTextStyle.title2
                    )
                }
            }
        },
        navigationIcon = {
            topBarState.navigationIcon?.let { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(action.icon),
                        tint = VoltechColor.foregroundPrimary,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            topBarState.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(action.icon),
                        tint = VoltechColor.foregroundPrimary,
                        contentDescription = null
                    )
                }
            }
        }
    )
}