package com.tbc.profile.presentation.screen.watchlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.button.TertiaryButton
import com.tbc.core_ui.components.button.TertiaryCircleIconButton
import com.tbc.core_ui.components.checkbox.VoltechCheckBoxDefaults
import com.tbc.core_ui.components.empty_state.EmptyState
import com.tbc.core_ui.components.item.FeedItemCard
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetupTopBar(onSetupTopBar, viewModel::onEvent)

    LaunchedEffect(Unit) {
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is WatchlistSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            WatchlistSideEffect.NavigateBackToProfile -> {
                navigateBack()
            }

            is WatchlistSideEffect.NavigateToItemDetails -> navigateToItemDetails(sideEffect.itemId)
        }
    }

    if (state.isLoading) {
        LoadingScreen()
    } else if (state.favoriteItems.isEmpty()) {
        EmptyState(
            title = stringResource(R.string.there_s_nothing_here),
            subtitle = stringResource(R.string.favorite_empty_state),
            icon = R.drawable.ic_outlined_heart
        )
    } else {
        WatchlistContent(
            state = state,
            onEvent = viewModel::onEvent,
        )
    }


}

@Composable
private fun WatchlistContent(
    state: WatchlistState,
    onEvent: (WatchlistEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(VoltechColor.backgroundPrimary)
            .fillMaxSize()
    ) {
        val allSelected =
            state.favoriteItems.isNotEmpty() && state.favoriteItems.all { it.isSelected }
        val selectedCount = state.favoriteItems.count { it.isSelected }
        val anySelected = state.favoriteItems.any { it.isSelected }


        ItemDeletionSection(
            editModeOn = state.editModeOn,
            selectedCount = selectedCount,
            anySelected = anySelected,
            allSelected = allSelected,
            toggleAll = { onEvent(WatchlistEvent.ToggleSelectAll(it)) },
            turnEditModeOff = { onEvent(WatchlistEvent.EditModeOff) },
            turnEditModeOn = { onEvent(WatchlistEvent.EditModeOn) },
            deleteFavoriteItemById = { onEvent(WatchlistEvent.DeleteFavoriteItemById) },
        )

        Spacer(modifier = Modifier.height(Dimen.size8))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(state.favoriteItems) { item ->
                FeedItemCard(
                    imageUrl = item.images.first(),
                    title = item.title,
                    checked = item.isSelected,
                    price = item.price,
                    onRootClick = {
                        if (!state.editModeOn) {
                            onEvent(WatchlistEvent.NavigateToItemDetails(item.id))
                        } else {
                            onEvent(WatchlistEvent.ToggleItemForDeletion(item.favoriteId))
                        }
                    },
                    editModeOn = state.editModeOn
                )
            }
        }

    }


}

@Composable
private fun ItemDeletionSection(
    editModeOn: Boolean,
    selectedCount: Int,
    anySelected: Boolean,
    allSelected: Boolean,
    toggleAll: (Boolean) -> Unit,
    turnEditModeOff: () -> Unit,
    turnEditModeOn: () -> Unit,
    deleteFavoriteItemById: () -> Unit,
) {
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
                Checkbox(
                    modifier = Modifier.padding(start = Dimen.size4),
                    checked = allSelected,
                    onCheckedChange = { checked ->
                        toggleAll(checked)
                    },
                    colors = VoltechCheckBoxDefaults.primaryColors
                )

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


@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    onEvent: (WatchlistEvent) -> Unit,
) {
    val title = stringResource(id = R.string.watchlist)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = R.drawable.ic_arrow_back,
                    onClick = { onEvent(WatchlistEvent.NavigateBackToProfile) }
                )
            )
        )
    }
}