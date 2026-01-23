package com.tbc.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle

@Composable
fun VoltechButton(
    modifier: Modifier = Modifier,
    text: String,
    border: BorderStroke? = null,
    shape: Shape = RoundedCornerShape(Dimen.size6),
    buttonColor: Color = VoltechColor.primary,
//    textColor: Color = Black,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val content: @Composable () -> Unit = {
        Text(
            text = text,
            textAlign = TextAlign.Center,
//            color = textColor,
            style = VoltechTextStyle.body16Normal
        )
    }

    if (border != null) {
        OutlinedButton(
            modifier = modifier,
            border = border,
            shape = shape,
            onClick = onClick,
            enabled = enabled
        ) {
            content()
        }
    } else {
        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
            ),
            shape = shape,
            enabled = enabled,
            onClick = onClick
        ) {
            content()
        }
    }

//    else {
//        Button(
//            modifier = modifier,
//            colors = ButtonColors(
//                containerColor = buttonColor,
//                disabledContainerColor = buttonColor.copy(alpha = 0.5f),
//                disabledContentColor = buttonColor.copy(alpha = 0.5f),
////                disabledContentColor = textColor.copy(alpha = 0.5f),
//                contentColor = buttonColor.copy(alpha = 0.5f),
////                contentColor = textColor
//            ),
//            shape = shape,
//            enabled = enabled,
//            onClick = onClick
//        ) {
//            content()
//        }
//    }

}