package com.tbc.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.tbc.core.designsystem.theme.Dimen

@Composable
fun AppOutlinedButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    backgroundColor: Color,
    borderColor: Color,
){
    OutlinedButton(onClick = onClick,
        modifier = modifier,
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = BorderStroke(
            width = Dimen.size1,
            color = borderColor
        )
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}
