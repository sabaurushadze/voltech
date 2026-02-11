package com.tbc.selling.presentation.screen.my_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.empty_state.EmptyState
import com.tbc.core_ui.components.item.FeedItemCard
import com.tbc.core_ui.components.item_deletion.ItemDeletion
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.resource.R

@Composable
fun MyItemsScreen(
    viewModel: MyItemsViewModel = hiltViewModel(),
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToAddItem: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetupTopBar(onSetupTopBar)

    LaunchedEffect(Unit) {
        state.user?.let { user ->
            viewModel.onEvent(MyItemsEvent.GetMyItems(uid = user.uid))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(MyItemsEvent.CanUserPostItems)
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MyItemsSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            MyItemsSideEffect.NavigateToAddItem -> {
                navigateToAddItem()
            }

            is MyItemsSideEffect.NavigateToItemDetails -> navigateToItemDetails(sideEffect.id)
        }
    }


    if (state.myItems.isEmpty() && !state.isLoading) {
        EmptyState(
            title = "You currently have no listings",
            buttonText = "Add item",
            onButtonClick = { viewModel.onEvent(MyItemsEvent.NavigateToAddItem) },
        )
    } else if (state.isLoading) {
        LoadingScreen()
    } else {
        MyItemsContent(
            state = state,
            onEvent = viewModel::onEvent,
        )
    }

}

@Composable
private fun MyItemsContent(
    state: MyItemsState,
    onEvent: (MyItemsEvent) -> Unit,
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
            toggleAll = { onEvent(MyItemsEvent.ToggleSelectAll(it)) },
            turnEditModeOff = { onEvent(MyItemsEvent.EditModeOff) },
            turnEditModeOn = { onEvent(MyItemsEvent.EditModeOn) },
            deleteFavoriteItemById = { onEvent(MyItemsEvent.DeleteFavoriteItemById) },
        )

        Spacer(modifier = Modifier.height(Dimen.size8))

        LazyColumn {
            items(state.myItems) { item ->
                FeedItemCard(
                    title = item.title,
                    price = item.price,
                    checked = item.isSelected,
                    editModeOn = state.editModeOn,
                    condition = stringResource(item.condition),
                    location = stringResource(item.location),
                    imageUrl = item.images.firstOrNull(),
                    onRootClick = {
                        if (!state.editModeOn) {
                            onEvent(MyItemsEvent.NavigateToItemDetails(item.id))
                        } else {
                            onEvent(MyItemsEvent.ToggleItemForDeletion(item.id))
                        }
                    }
                )
            }

            if (state.userCanAddItem) {
                item {
                    Spacer(modifier = Modifier.height(Dimen.size32))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PrimaryButton(
                            text = stringResource(R.string.add_item),
                            onClick = {
                                onEvent(MyItemsEvent.EditModeOff)
                                onEvent(MyItemsEvent.NavigateToAddItem)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimen.size32))
                }
            }


        }
    }
}


@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val title = stringResource(id = R.string.my_items)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
            )
        )
    }
}