package com.tbc.search.presentation.screen.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tbc.core.designsystem.components.loading.LoadingOverlay
import com.tbc.core.designsystem.components.textfield.TextInputFieldDummy
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.presentation.extension.collectEvent
import com.tbc.search.presentation.R
import com.tbc.search.presentation.components.feed.FeedItemCard
import com.tbc.search.presentation.extension.isScrollingUp
import com.tbc.search.presentation.model.feed.UiFeedItem

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onShowSnackBar: (String) -> Unit,
    navigateToSearch: () -> Unit,
    query: String,
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.feedPagingFlow.collectAsLazyPagingItems()
    val isContentReady = pagingItems.loadState.refresh !is LoadState.Loading

    val listState = rememberLazyListState()

    LaunchedEffect(query) {
        viewModel.onEvent(FeedEvent.SaveSearchQuery(query))
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

    if (!isContentReady) {
        LoadingOverlay()
    } else {
        FeedContent(
            state = state,
            pagingItems = pagingItems,
            onEvent = viewModel::onEvent,
            onSearchClick = navigateToSearch,
            listState = listState
        )
    }
}



@Composable
private fun FeedContent(
    modifier: Modifier = Modifier,
    state: FeedState,
    pagingItems: LazyPagingItems<UiFeedItem>,
    onEvent: (FeedEvent) -> Unit,
    onSearchClick: () -> Unit,
    listState: LazyListState,
) {
    Column() {
        AnimatedVisibility(
            visible = listState.isScrollingUp().value
        ) {
            TextInputFieldDummy(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.size16),
                label = stringResource(R.string.search_on_voltech),
                shape = VoltechRadius.radius24,
                onClick = onSearchClick,
            )
        }

        Spacer(modifier = Modifier.height(Dimen.size8))

        LazyColumn(
            state = listState,
            modifier = modifier
                .systemBarsPadding(),
            contentPadding = PaddingValues(horizontal = Dimen.size8)
        ) {
            items(
                count = pagingItems.itemCount,
                key = { index ->
                    pagingItems[index]?.id ?: index
                }
            ) { index ->
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