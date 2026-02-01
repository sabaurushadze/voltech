package com.tbc.home.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.tbc.core.designsystem.components.topbar.TopBarAction
import com.tbc.core.designsystem.components.topbar.TopBarState
import com.tbc.core.designsystem.theme.VoltechTextStyle

@Composable
fun HomeScreen(
    onSetupTopBar: (TopBarState) -> Unit,
) {
    SetupTopBar(onSetupTopBar)


    Text(text = "Home Screen!", style = VoltechTextStyle.title18Bold)
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
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = {  }
                )
            )
        )
    }
}