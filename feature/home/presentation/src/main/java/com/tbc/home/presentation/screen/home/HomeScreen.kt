package com.tbc.home.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.extension.capitalizeFirst
import com.tbc.core_ui.components.button.BorderlessIconButton
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.pull_to_refresh.VoltechPullToRefresh
import com.tbc.core_ui.screen.internet.NoInternetConnection
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.home.presentation.model.category.UiCategoryItem
import com.tbc.home.presentation.model.recently_viewed.UiRecentlyItem
import com.tbc.resource.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToFeed: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToRecentlyViewed: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        onEvent(HomeEvent.GetCategories)
        onEvent(HomeEvent.GetRecentlyViewedItems)
    }

    if (state.showNoConnectionError) {
        NoInternetConnection {
            onEvent(HomeEvent.GetCategories)
            onEvent(HomeEvent.GetRecentlyViewedItems)
        }
    } else {
        VoltechPullToRefresh(
            state = pullToRefreshState,
            isRefreshing = state.isRefreshing,
            onRefresh = {
                viewModel.onEvent(HomeEvent.GetCategories)
                viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
            },
        ) {
            HomeContent(
                state = state,
                navigateToFeed = navigateToFeed,
                navigateToItemDetails = navigateToItemDetails,
                navigateToRecentlyViewed = navigateToRecentlyViewed,
            )
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    navigateToFeed: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToRecentlyViewed: () -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimen.size24)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(start = Dimen.size16),
                    text = stringResource(R.string.categories),
                    style = VoltechTextStyle.title2,
                    color = VoltechColor.foregroundPrimary
                )

                Spacer(Modifier.height(Dimen.size32))

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.size350),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.size12),
                    verticalArrangement = Arrangement.spacedBy(Dimen.size12),
                    contentPadding = PaddingValues(horizontal = Dimen.size16)
                ) {
                    items(state.categoryList) { category ->
                        CategoryItem(
                            categoryItem = category,
                            navigateToFeed = navigateToFeed
                        )
                    }
                }

                Spacer(Modifier.height(Dimen.size24))

                if (state.recentlyViewedItems.isNotEmpty()) {
                    RecentlyViewedHeader(navigateToRecentlyViewed)
                }

                Spacer(Modifier.height(Dimen.size32))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = Dimen.size16),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.size12)
                ) {
                    items(state.recentlyViewedItems) { item ->
                        RecentlyViewedItem(
                            recentlyViewedItems = item,
                            navigateToItemDetails = navigateToItemDetails
                        )
                    }
                }
            }
        }

        item {
            Spacer(Modifier.height(Dimen.size24))
        }

    }
}

@Composable
private fun RecentlyViewedHeader(
    navigateToRecentlyViewed: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = Dimen.size16),
            text = stringResource(R.string.your_recently_viewed),
            style = VoltechTextStyle.title2,
            color = VoltechColor.foregroundPrimary
        )

        BorderlessIconButton(
            text = stringResource(R.string.see_all),
            icon = R.drawable.ic_arrow_right,
            onClick = { navigateToRecentlyViewed() }
        )
    }
}

@Composable
private fun RecentlyViewedItem(
    recentlyViewedItems: UiRecentlyItem,
    navigateToItemDetails: (Int) -> Unit,
) = with(recentlyViewedItems) {
    Column(
        modifier = Modifier
            .width(Dimen.size150)
            .clickable { navigateToItemDetails(id) }
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.size150)
                .clip(VoltechRadius.radius24)
                .background(VoltechFixedColor.lightGray)
        ) {
            images.firstOrNull()?.let { firstImageUrl ->
                BaseAsyncImage(
                    modifier = Modifier.align (Alignment.Center),
                    url = firstImageUrl,
                )
            }
        }

        Spacer(Modifier.height(Dimen.size24))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(Modifier.height(Dimen.size4))

        Text(
            text = price,
            style = VoltechTextStyle.title3,
            color = VoltechColor.foregroundPrimary
        )
    }
}


@Composable
private fun CategoryItem(
    categoryItem: UiCategoryItem,
    navigateToFeed: (String) -> Unit,
) = with(categoryItem) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { navigateToFeed(category.name) }
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.size100)
                .clip(CircleShape)
                .background(VoltechFixedColor.lightGray),
        ) {
            image?.let {
                BaseAsyncImage(
                    url = image,
                    modifier = Modifier
                        .size(Dimen.size80)
                        .align(Alignment.Center),
                    placeholderRes = R.drawable.circle_placeholder
                )
            }
        }

        Spacer(Modifier.height(Dimen.size12))

        Text(
            text = stringResource(categoryNameRes).capitalizeFirst(),
            style = VoltechTextStyle.title3,
            color = VoltechColor.foregroundPrimary,
            textAlign = TextAlign.Center
        )
    }
}