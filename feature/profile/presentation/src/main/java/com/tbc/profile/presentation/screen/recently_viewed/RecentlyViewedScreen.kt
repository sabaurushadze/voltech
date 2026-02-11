package com.tbc.profile.presentation.screen.recently_viewed

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
fun RecentlyViewedScreen(
    viewModel: RecentlyViewedViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

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
                navigateToProfile()
            }

            is RecentlyViewedSideEffect.NavigateToItemDetails -> navigateToItemDetails(sideEffect.itemId)
        }
    }

    if (state.isLoading) {
        LoadingScreen()
    } else if (state.recentlyViewedItems.isEmpty()) {
        EmptyState(
            title = stringResource(R.string.there_s_nothing_here),
            subtitle = stringResource(R.string.recently_empty_state),
            icon = R.drawable.ic_history
        )
    } else {
        RecentlyViewedContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }


    SetupTopBar(
        onSetupTopBar = onSetupTopBar,
        navigateToProfile = navigateToProfile
    )


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
                    imageUrl = item.images.firstOrNull(),
                    title = item.title,
                    checked = item.isSelected,
                    price = item.price,
                    onRootClick = {
                        if (!state.editModeOn) {
                            onEvent(RecentlyViewedEvent.NavigateToItemDetails(item.id))
                        } else {
                            onEvent(RecentlyViewedEvent.ToggleItemForDeletion(item.favoriteId))
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
    navigateToProfile: () -> Unit,
) {
    val title = stringResource(id = R.string.recently_viewed)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = R.drawable.ic_arrow_back,
                    onClick = { navigateToProfile() }
                )
            )
        )
    }
}