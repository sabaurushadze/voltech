package com.tbc.profile.presentation.screen.recently_viewed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.tbc.core_ui.components.item.FeedItemCard
import com.tbc.core_ui.components.item_deletion.ItemDeletion
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.pull_to_refresh.VoltechPullToRefresh
import com.tbc.core_ui.screen.empty_state.EmptyState
import com.tbc.core_ui.screen.internet.NoInternetConnection
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.resource.R

@Composable
fun RecentlyViewedScreen(
    viewModel: RecentlyViewedViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()


    LaunchedEffect(Unit) {
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RecentlyViewedSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            RecentlyViewedSideEffect.NavigateBackToProfile -> {
                navigateToBack()
            }

            is RecentlyViewedSideEffect.NavigateToItemDetails -> navigateToItemDetails(sideEffect.itemId)
        }
    }

    if (state.showNoConnectionError) {
        NoInternetConnection { viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems) }
    } else if (state.isLoading) {
        LoadingScreen()
    } else if (state.recentlyViewedItems.isEmpty()) {
        EmptyState(
            title = stringResource(R.string.there_s_nothing_here),
            subtitle = stringResource(R.string.recently_empty_state),
            icon = R.drawable.ic_history
        )
    } else {
        VoltechPullToRefresh(
            state = pullToRefreshState,
            onRefresh = { viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems) },
        ) {
            RecentlyViewedContent(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
private fun RecentlyViewedContent(
    state: RecentlyViewedState,
    onEvent: (RecentlyViewedEvent) -> Unit,
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
            toggleAll = { onEvent(RecentlyViewedEvent.ToggleSelectAll(it)) },
            turnEditModeOff = { onEvent(RecentlyViewedEvent.EditModeOff) },
            turnEditModeOn = { onEvent(RecentlyViewedEvent.EditModeOn) },
            deleteFavoriteItemById = { onEvent(RecentlyViewedEvent.DeleteRecentlyItemById) },
        )

        Spacer(modifier = Modifier.height(Dimen.size8))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(state.recentlyViewedItems) { item ->
                FeedItemCard(
                    imagesList = item.images,
                    title = item.title,
                    checked = item.isSelected,
                    price = item.price,
                    onRootClick = {
                        if (!state.editModeOn) {
                            onEvent(RecentlyViewedEvent.NavigateToItemDetails(item.id))
                        } else {
                            onEvent(RecentlyViewedEvent.ToggleItemForDeletion(item.recentlyId))
                        }
                    },
                    editModeOn = state.editModeOn
                )
            }
        }

    }
}

