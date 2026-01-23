package com.tbc.home.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.tbc.core.designsystem.theme.VoltechTextStyle

@Composable
fun HomeScreen(
    onShowSnackBar: (String) -> Unit,

    ) {
    Text(text = "Home Screen!", style = VoltechTextStyle.title18Bold)
}

