package com.tbc.home.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
fun HomeScreen(
    onSetupTopBar: (TopBarState) -> Unit,
) {
    SetupTopBar(onSetupTopBar)


    Text(text = "Home Screen!", style = VoltechTextStyle.title2)
}

@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val title = "title"

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = R.drawable.ic_arrow_back,
                    onClick = { }
                )
            )
        )
    }
}