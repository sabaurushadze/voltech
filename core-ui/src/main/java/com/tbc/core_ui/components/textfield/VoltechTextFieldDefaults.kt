package com.tbc.core_ui.components.textfield

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechTextFieldDefaults {

    val primaryColors: TextFieldColors
        @Composable
        get() = TextFieldDefaults.colors(
            focusedContainerColor = VoltechColor.backgroundTertiary,
            unfocusedContainerColor = VoltechColor.backgroundTertiary,
            focusedIndicatorColor = VoltechColor.backgroundTertiary,
            unfocusedIndicatorColor = VoltechColor.backgroundTertiary,
            disabledIndicatorColor = VoltechColor.backgroundTertiary,
            disabledContainerColor = VoltechColor.backgroundDisabled,
            focusedLabelColor = VoltechColor.foregroundAccent,
            unfocusedLabelColor = VoltechColor.foregroundAccent,
            focusedTextColor = VoltechColor.foregroundPrimary,
            unfocusedTextColor = VoltechColor.foregroundSecondary,
            cursorColor = VoltechColor.foregroundAccent,
            errorCursorColor = VoltechColor.foregroundAttention,
            errorTextColor = VoltechColor.foregroundPrimary,
            errorLabelColor = VoltechColor.foregroundPrimary,
        )
}

object VoltechOutlinedTextFieldDefaults {

    val primaryColors: TextFieldColors
        @Composable
        get() = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = VoltechColor.foregroundAccent,
            unfocusedBorderColor = VoltechColor.foregroundSecondary,
            focusedLabelColor = VoltechColor.foregroundAccent,
            unfocusedLabelColor = VoltechColor.foregroundPrimary,
            focusedTextColor = VoltechColor.foregroundPrimary,
            unfocusedTextColor = VoltechColor.foregroundSecondary,
            cursorColor = VoltechColor.foregroundAccent,
            errorCursorColor = VoltechColor.foregroundAttention,
            errorTextColor = VoltechColor.foregroundPrimary,
            errorLabelColor = VoltechColor.foregroundPrimary,
        )
}