package com.tbc.core_ui.components.dropdown

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.tbc.core_ui.theme.VoltechColor

object VoltechDropDownDefaults {

    @OptIn(ExperimentalMaterial3Api::class)
    val primaryColors: TextFieldColors
        @Composable
        get() = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = VoltechColor.backgroundPrimary,
            unfocusedContainerColor = VoltechColor.backgroundPrimary,
            focusedTextColor = VoltechColor.foregroundPrimary,
            unfocusedTextColor = VoltechColor.foregroundSecondary,
            cursorColor = VoltechColor.foregroundAccent,
            focusedTrailingIconColor = VoltechColor.foregroundAccent,
            unfocusedTrailingIconColor = VoltechColor.foregroundSecondary,
            focusedLabelColor = VoltechColor.foregroundAccent,
            unfocusedLabelColor = VoltechColor.foregroundPrimary,
            focusedIndicatorColor = VoltechColor.foregroundAccent,
            unfocusedIndicatorColor = VoltechColor.foregroundSecondary,
            focusedPrefixColor = VoltechColor.foregroundAccent,
            focusedSuffixColor = VoltechColor.foregroundAccent,
            errorLabelColor = VoltechColor.foregroundPrimary,
            errorTextColor = VoltechColor.foregroundPrimary,
            errorContainerColor = VoltechColor.backgroundPrimary,
            errorIndicatorColor = VoltechColor.foregroundAttention
        )
}
