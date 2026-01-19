package com.tbc.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.tbc.designsystem.theme.TextStyles

@Composable
fun HomeScreen(
    onShowSnackBar: (String) -> Unit,

) {
    Text(text = "Home Screen!", style = TextStyles.headlineLarge)
}



