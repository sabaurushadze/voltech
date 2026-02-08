package com.tbc.search.presentation.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.item.FeedItemCard
import com.tbc.core_ui.components.item.FeedItemPlaceholderCard
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.search.presentation.components.feed.sheet.FilterBottomSheet
import com.tbc.search.presentation.components.feed.sheet.SortBottomSheet
import com.tbc.search.presentation.components.feed.topbar.FeedAppBar
import com.tbc.search.presentation.enums.feed.SortType
import com.tbc.search.presentation.model.feed.UiFeedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    query: String?,
    categoryQuery: String?,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.feedPagingFlow.collectAsLazyPagingItems()
    val isContentReady = pagingItems.loadState.refresh !is LoadState.Loading

    val sortBottomSheetState = rememberModalBottomSheetState()
    val filterBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    val listState = rememberLazyListState()

    LaunchedEffect(query) {
        query?.let {
            if (query.isNotEmpty()) {
                viewModel.onEvent(FeedEvent.SaveSearchQuery(query))
            }
        }
    }

    LaunchedEffect(categoryQuery) {
        if (state.query.category == null){
            categoryQuery?.let { category->
                viewModel.onEvent(FeedEvent.SaveCategoryQuery(category))
            }
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        if (pagingItems.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FeedSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            is FeedSideEffect.NavigateToItemDetails -> {
                navigateToItemDetails(sideEffect.id)
            }
        }
    }

    FeedContent(
        state = state,
        pagingItems = pagingItems,
        onEvent = viewModel::onEvent,
        listState = listState,
        isContentReady = isContentReady,
        topAppBarScrollBehavior = topAppBarScrollBehavior,
        navigateToSearch = navigateToSearch
    )

    if (state.selectedSort) {
        ModalBottomSheet(
            containerColor = VoltechColor.backgroundSecondary,
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
            containerColor = VoltechColor.backgroundSecondary,
            onDismissRequest = { viewModel.onEvent(FeedEvent.HideFilterSheet) },
            sheetState = filterBottomSheetState
        ) {

            FilterBottomSheet(
                state = state,
                currentQuery = state.query.titleLike.orEmpty(),
                onEvent = viewModel::onEvent,
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
    navigateToSearch: () -> Unit,
    listState: LazyListState,
    isContentReady: Boolean,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {
    Column(
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .background(VoltechColor.backgroundPrimary)
            .fillMaxSize()
    ) {
        FeedAppBar(
            onSearchClick = navigateToSearch,
            onSortClick = { onEvent(FeedEvent.ShowSortSheet) },
            onFilterClick = { onEvent(FeedEvent.ShowFilterSheet) },
            isLoading = !isContentReady,
            scrollBehavior = topAppBarScrollBehavior,
            isContentReady = isContentReady,
        )

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
                        imageUrl = it.images.first(),
                        title = it.title,
                        condition = stringResource(it.conditionRes),
                        price = it.price,
                        location = stringResource(it.locationRes),
//                        isFavoriteIconSelected = false,
//                        onFavoriteIconClick = { },
                        onRootClick = { onEvent(FeedEvent.FeedItemClick(item.id)) }
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.size4))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = VoltechBorder.medium,
                    color = VoltechColor.borderSubtle
                )

                Spacer(modifier = Modifier.height(Dimen.size4))

            }
        }

    }

}