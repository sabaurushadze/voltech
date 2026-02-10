package com.tbc.core_ui.components.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownMenu(
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    shape: RoundedCornerShape = VoltechRadius.radius16,
    errorText: String = "",
    itemText: @Composable (T) -> String,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager: FocusManager = LocalFocusManager.current

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { shouldExpand ->
                expanded = shouldExpand
                if (!shouldExpand) {
                    focusManager.clearFocus(force = true)
                }
            }
        ) {

            OutlinedTextField(
                value = selectedItem?.let { itemText(it) } ?: "",
                onValueChange = {},
                shape = shape,
                readOnly = true,
                isError = errorText.isNotEmpty(),
                label = {
                    Text(
                        text = label,
                        style = VoltechTextStyle.body,
                    )
                },
                colors = VoltechDropDownDefaults.primaryColors,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )

            ExposedDropdownMenu(
                containerColor = VoltechColor.backgroundSecondary,
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    focusManager.clearFocus(force = true)
                }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemText(item),
                                style = VoltechTextStyle.body,
                                color = VoltechColor.foregroundPrimary
                            )
                        },
                        colors = MenuItemColors(
                            textColor = VoltechColor.foregroundPrimary,
                            leadingIconColor = VoltechColor.foregroundPrimary,
                            trailingIconColor = VoltechColor.foregroundPrimary,
                            disabledTextColor = VoltechColor.foregroundPrimary,
                            disabledLeadingIconColor = VoltechColor.foregroundPrimary,
                            disabledTrailingIconColor = VoltechColor.foregroundPrimary,
                        ),
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                            focusManager.clearFocus(force = true)
                        }
                    )
                }
            }
        }
        if (errorText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Dimen.size4))
            Text(
                text = errorText,
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundAttention
            )
        }
    }
}
