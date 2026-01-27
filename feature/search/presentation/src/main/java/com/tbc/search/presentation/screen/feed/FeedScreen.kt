package com.tbc.search.presentation.screen.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tbc.core.designsystem.components.button.IconTextButton
import com.tbc.core.designsystem.components.textfield.TextInputFieldDummy
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.presentation.extension.collectEvent
import com.tbc.search.presentation.R
import com.tbc.search.presentation.components.feed.items.FeedItemCard
import com.tbc.search.presentation.components.feed.items.FeedItemPlaceholderCard
import com.tbc.search.presentation.components.feed.sheet.FilterBottomSheet
import com.tbc.search.presentation.components.feed.sheet.SortBottomSheet
import com.tbc.search.presentation.enums.feed.SortType
import com.tbc.search.presentation.extension.isScrollingUp
import com.tbc.search.presentation.model.feed.UiFeedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onShowSnackBar: (String) -> Unit,
    navigateToSearch: () -> Unit,
    query: String,
    bottomAppBarScrollBehavior: BottomAppBarScrollBehavior,
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.feedPagingFlow.collectAsLazyPagingItems()
    val isContentReady = pagingItems.loadState.refresh !is LoadState.Loading

    val sortBottomSheetState = rememberModalBottomSheetState()
    val filterBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val listState = rememberLazyListState()

    LaunchedEffect(query) {
        viewModel.onEvent(FeedEvent.SaveSearchQuery(query))
    }

    LaunchedEffect(state.selectedSortType) {
        listState.scrollToItem(0)
    }

    LaunchedEffect(state.selectedFilter) {
        listState.scrollToItem(0)
    }

    viewModel.sideEffect.collectEvent { sideEffect ->
        when (sideEffect) {
            is FeedSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                onShowSnackBar(error)
            }

            is FeedSideEffect.NavigateToItemDetails -> {}
        }
    }

    FeedContent(
        state = state,
        pagingItems = pagingItems,
        onEvent = viewModel::onEvent,
        onSearchClick = navigateToSearch,
        listState = listState,
        isContentReady = isContentReady,
        bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
    )

    if (state.selectedSort) {
        ModalBottomSheet(
            containerColor = VoltechColor.background,
            onDismissRequest = { viewModel.onEvent(FeedEvent.HideSortSheet) },
            sheetState = sortBottomSheetState
        ) {
            SortBottomSheet(
                options = listOf(SortType.PRICE_LOWEST, SortType.PRICE_HIGHEST),
                selectedSortType = state.selectedSortType,
                onItemClick = { sortType ->
                    viewModel.onEvent(FeedEvent.SelectSortType(sortType))
                },
            )
        }
    }

    if (state.selectedFilter) {
        ModalBottomSheet(
            containerColor = VoltechColor.background,
            onDismissRequest = { viewModel.onEvent(FeedEvent.HideFilterSheet) },
            sheetState = filterBottomSheetState
        ) {
            FilterBottomSheet(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedContent(
    modifier: Modifier = Modifier,
    state: FeedState,
    pagingItems: LazyPagingItems<UiFeedItem>,
    onEvent: (FeedEvent) -> Unit,
    onSearchClick: () -> Unit,
    listState: LazyListState,
    isContentReady: Boolean,
    bottomAppBarScrollBehavior: BottomAppBarScrollBehavior,
) {
    Column(
        modifier = Modifier
            .nestedScroll(bottomAppBarScrollBehavior.nestedScrollConnection)
            .background(VoltechColor.background)
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = listState.isScrollingUp().value
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextInputFieldDummy(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.size16),
                    label = stringResource(R.string.search_on_voltech),
                    shape = VoltechRadius.radius24,
                    startIcon = ImageVector.vectorResource(R.drawable.ic_search),
                    onClick = onSearchClick,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.size16),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isContentReady) {
                        Text(
                            text = stringResource(R.string.loading_searching),
                            style = VoltechTextStyle.body16Bold,
                            color = VoltechColor.onBackground
                        )
                    } else {
                        Spacer(modifier = Modifier)
                    }
                    Row {
                        IconTextButton(
                            icon = ImageVector.vectorResource(R.drawable.ic_filter),
                            textRes = R.string.sort,
                            loading = state.isLoading,
                            enabled = !state.isLoading,
                            onClick = { onEvent(FeedEvent.ShowSortSheet) },
                        )
                        IconTextButton(
                            icon = ImageVector.vectorResource(R.drawable.ic_sort),
                            textRes = R.string.filter,
                            loading = state.isLoading,
                            enabled = !state.isLoading,
                            onClick = { onEvent(FeedEvent.ShowFilterSheet) },
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(Dimen.size8))

        LazyColumn(
            modifier = modifier
                .systemBarsPadding(),
            state = listState,
            userScrollEnabled = isContentReady,
            contentPadding = PaddingValues(horizontal = Dimen.size8)
        ) {
            if (!isContentReady) {
                items(10) {
                    FeedItemPlaceholderCard()

                    Spacer(modifier = Modifier.height(Dimen.size4))
                }
            }

            items(
                count = pagingItems.itemCount, key = { index ->
                    pagingItems[index]?.id ?: index
                }) { index ->
                val item = pagingItems[index]

                item?.let {
                    FeedItemCard(
                        imageUrl = it.image,
                        title = it.title,
                        condition = stringResource(it.conditionRes),
                        price = it.price,
                        location = stringResource(it.locationRes),
                        isFavoriteIconSelected = false,
                        onFavoriteIconClick = { },
                        onRootClick = { }
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.size4))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = Dimen.size1,
                    color = VoltechColor.neutral1
                )

                Spacer(modifier = Modifier.height(Dimen.size4))

            }
        }

    }

}