package com.tbc.profile.presentation.screen.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.empty_state.EmptyState
import com.tbc.core_ui.components.item.FeedItemCard
import com.tbc.core_ui.components.item_deletion.ItemDeletion
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
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
        ItemDeletion(
            editModeOn = state.editModeOn,
            selectedCount = state.selectedCount,
            anySelected = state.anySelected,
            allSelected = state.allSelected,
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
                    imageUrl = item.images.firstOrNull(),
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