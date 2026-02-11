package com.tbc.core_ui.components.item_deletion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import com.tbc.core_ui.components.button.TertiaryButton
import com.tbc.core_ui.components.button.TertiaryCircleIconButton
import com.tbc.core_ui.components.checkbox.VoltechCheckBoxDefaults
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
fun ItemDeletion(
    editModeOn: Boolean,
    selectedCount: Int,
    anySelected: Boolean,
    allSelected: Boolean,
    toggleAll: (Boolean) -> Unit,
    turnEditModeOff: () -> Unit,
    turnEditModeOn: () -> Unit,
    deleteFavoriteItemById: () -> Unit,
) {
    val checkboxState = when {
        allSelected -> ToggleableState.On
        anySelected -> ToggleableState.Indeterminate
        else -> ToggleableState.Off
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (editModeOn) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TriStateCheckbox(
                    modifier = Modifier.padding(start = Dimen.size4),
                    state = checkboxState,
                    onClick = {
                        toggleAll(!allSelected)
                    },
                    colors = VoltechCheckBoxDefaults.primaryColors,
                )
//                Checkbox(
//                    modifier = Modifier.padding(start = Dimen.size4),
//                    checked = allSelected,
//                    onCheckedChange = { checked ->
//                        toggleAll(checked)
//                    },
//                    colors = VoltechCheckBoxDefaults.primaryColors
//                )

                Text(
                    text = if (selectedCount > 0) stringResource(
                        R.string.selected_item,
                        selectedCount
                    ) else stringResource(R.string.select_all),
                    style = VoltechTextStyle.body,
                    color = VoltechColor.foregroundPrimary
                )

            }
        }

        if (editModeOn) {
            Text(
                modifier = Modifier
                    .padding(end = Dimen.size16)
                    .clickable {
                        turnEditModeOff()
                    },
                text = stringResource(R.string.cancel),
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundPrimary
            )
        } else {
            TertiaryCircleIconButton(
                onClick = {
                    turnEditModeOn()
                },
                size = Dimen.size16,
                iconColor = VoltechColor.foregroundPrimary,
                icon = R.drawable.ic_edit,
                backgroundColor = VoltechColor.backgroundTertiary,
            )
        }
    }

    if (editModeOn) {
        TertiaryButton(
            modifier = Modifier
                .padding(start = Dimen.size16)
                .height(Dimen.size40),
            enabled = anySelected,
            text = stringResource(R.string.delete),
            border = BorderStroke(
                width = VoltechBorder.medium,
                color = if (anySelected) VoltechColor.foregroundPrimary
                else VoltechColor.backgroundDisabled
            ),
            onClick = {
                deleteFavoriteItemById()
                turnEditModeOff()
            }
        )
    }
}