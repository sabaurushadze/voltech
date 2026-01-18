package com.tbc.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tbc.designsystem.theme.Black

@Composable
fun VoltechButton(
    modifier: Modifier = Modifier,
    text: String = "",
    border: BorderStroke? = null,
    shape: Shape = RoundedCornerShape(4.dp),
    buttonColor: Color = Black,
    textColor: Color = Black,
    textSize: TextUnit = 14.sp,
    onClick: () -> Unit,
) {
    val content: @Composable () -> Unit = {
        Text(
            text = text,
            fontSize = textSize,
            textAlign = TextAlign.Center,
            color = textColor
        )
    }

    if (border != null) {
        OutlinedButton(
            modifier = modifier,
            border = border,
            shape = shape,
            onClick = onClick
        ) {
            content()
        }
    } else {
        Button(
            modifier = modifier,
            colors = ButtonColors(
                containerColor = buttonColor,
                disabledContainerColor = buttonColor.copy(alpha = 0.5f),
                disabledContentColor = textColor.copy(alpha = 0.5f),
                contentColor = textColor
            ),
            shape = shape,
            onClick = onClick
        ) {
            content()
        }
    }

}