package com.tbc.core_ui.components.item_deletion

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import com.tbc.core_ui.components.button.BorderlessButton
import com.tbc.core_ui.components.button.TertiaryButton
import com.tbc.core_ui.components.checkbox.VoltechCheckBoxDefaults
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@OptIn(ExperimentalAnimationApi::class)
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

    val rowHeight = Dimen.size40

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = editModeOn,
                enter = fadeIn(tween(200)) + slideInHorizontally { -it },
                exit = fadeOut(tween(200)) + slideOutHorizontally { -it }
            ) {
                Row(
                    modifier = Modifier
                        .clickable { toggleAll(!allSelected) },
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TriStateCheckbox(
                        state = checkboxState,
                        onClick = { toggleAll(!allSelected) },
                        colors = VoltechCheckBoxDefaults.primaryColors,
                    )

                    Text(
                        modifier = Modifier.padding(end = Dimen.size16),
                        text = if (selectedCount > 0)
                            stringResource(R.string.selected_item, selectedCount)
                        else
                            stringResource(R.string.select_all),
                        style = VoltechTextStyle.body,
                        color = VoltechColor.foregroundPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            AnimatedContent(
                targetState = editModeOn,
                transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(200)) }
            ) { isEditMode ->

                if (isEditMode) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        TertiaryButton(
                            modifier = Modifier.height(Dimen.size40),
                            enabled = anySelected,
                            text = stringResource(R.string.delete),
                            border = BorderStroke(
                                width = VoltechBorder.medium,
                                color = if (anySelected)
                                    VoltechColor.foregroundPrimary
                                else
                                    VoltechColor.backgroundDisabled
                            ),
                            onClick = {
                                deleteFavoriteItemById()
                                turnEditModeOff()
                            }
                        )

                        Spacer(modifier = Modifier.width(Dimen.size4))

                        BorderlessButton(
                            text = stringResource(R.string.cancel),
                            onClick = { turnEditModeOff() }
                        )
                    }
                } else {
                    TertiaryButton(
                        modifier = Modifier.padding(end = Dimen.size8),
                        text = stringResource(R.string.edit),
                        onClick = { turnEditModeOn() },
                    )
                }
            }
        }
    }
}