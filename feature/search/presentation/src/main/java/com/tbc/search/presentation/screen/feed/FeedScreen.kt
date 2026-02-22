package com.tbc.search.presentation.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.item.FeedItemCard
import com.tbc.core_ui.components.item.FeedItemPlaceholderCard
import com.tbc.core_ui.components.loading.LoadingIcon
import com.tbc.core_ui.components.pull_to_refresh.VoltechPullToRefresh
import com.tbc.core_ui.screen.empty_state.EmptyState
import com.tbc.core_ui.screen.internet.NoInternetConnection
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.resource.R
import com.tbc.search.presentation.components.feed.sheet.feed.FilterBottomSheet
import com.tbc.search.presentation.components.feed.sheet.feed.SortBottomSheet
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
    sellerUid: String?
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    val pagingItems = viewModel.feedPagingFlow.collectAsLazyPagingItems()
    val loadState = pagingItems.loadState
    val isRefreshing = loadState.refresh is LoadState.Loading

    val pullToRefreshState = rememberPullToRefreshState()

    val sortBottomSheetState = rememberModalBottomSheetState()
    val filterBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    val listState = rememberLazyListState()

    LaunchedEffect(query) {
        query?.let {
            if (query.isNotEmpty()) {
                onEvent(FeedEvent.SaveSearchQuery(query))
            }
        }
    }

    LaunchedEffect(categoryQuery) {
        if (state.query.category == null) {
            categoryQuery?.let { category ->
                onEvent(FeedEvent.SaveCategoryQuery(category))
            }
        }
    }

    LaunchedEffect(sellerUid) {
        sellerUid?.let {
            onEvent(FeedEvent.GetSellerItemsByUid(sellerUid))
        }
    }

    LaunchedEffect(pagingItems.loadState.refresh) {
        if (isRefreshing) {
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


    VoltechPullToRefresh(
        state = pullToRefreshState,
        onRefresh = { pagingItems.refresh() },
    ) {

        FeedContent(
            pagingItems = pagingItems,
            onEvent = viewModel::onEvent,
            listState = listState,
            isRefreshing = isRefreshing,
            searchQuery = query,
            topAppBarScrollBehavior = topAppBarScrollBehavior,
            navigateToSearch = navigateToSearch
        )
    }

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
    searchQuery: String?,
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<UiFeedItem>,
    onEvent: (FeedEvent) -> Unit,
    navigateToSearch: () -> Unit,
    listState: LazyListState,
    isRefreshing: Boolean,
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
            isLoading = isRefreshing,
            scrollBehavior = topAppBarScrollBehavior,
            isRefreshing = isRefreshing,
            searchQuery = searchQuery.orEmpty()
        )

        LazyColumn(
            modifier = modifier
                .systemBarsPadding(),
            state = listState,
            userScrollEnabled = !isRefreshing,
            contentPadding = PaddingValues(horizontal = Dimen.size8),
        ) {
            if (pagingItems.loadState.refresh is LoadState.Error && pagingItems.itemCount == 0) {
                item {
                    NoInternetConnection {
                        pagingItems.retry()
                    }
                }
            }

            if (isRefreshing) {
                items(10) {
                    FeedItemPlaceholderCard()

                    Spacer(modifier = Modifier.height(Dimen.size4))
                }
            } else if (pagingItems.loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyState(
                            title = stringResource(R.string.no_exact_matches_found),
                            icon = R.drawable.ic_shopping_cart
                        )
                    }
                }
            }
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }

            ) { index ->
                val item = pagingItems[index]

                item?.let {
                    FeedItemCard(
                        imagesList = it.images,
                        title = it.title,
                        condition = stringResource(it.conditionRes),
                        price = it.price,
                        location = stringResource(it.locationRes),
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

            when (pagingItems.loadState.append) {
                is LoadState.Error -> {
                    item {
                        NoInternetConnection {
                            pagingItems.retry()
                        }
                        Spacer(modifier = Modifier.height(Dimen.size48))
                    }
                }

                is LoadState.Loading -> {
                    item {
                        LoadingIcon()
                        Spacer(modifier = Modifier.height(Dimen.size48))
                    }
                }

                else -> {
                }
            }
        }

    }

}

