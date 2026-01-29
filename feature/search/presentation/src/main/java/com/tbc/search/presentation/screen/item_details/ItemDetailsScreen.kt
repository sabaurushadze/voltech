package com.tbc.search.presentation.screen.item_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTheme
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.search.presentation.components.feed.items.FeedItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    id: Int,
    viewModel: ItemDetailsViewModel = hiltViewModel(),
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(id))
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ItemDetailsSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }
        }
    }

    ItemDetailsContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemDetailsContent(
    state: ItemDetailsState,
    onEvent: (ItemDetailsEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VoltechColor.background)
            .systemBarsPadding()
    ) {
        LazyRow(

        ) {
            state.itemDetails?.let {
                items(state.itemDetails.images) { imageUrl ->
                    BaseAsyncImage(
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp),
                        url = imageUrl,
                    )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FeedItemWrapperPreview() {
    VoltechTheme() {
        FeedItemCard(
            isFavoriteIconSelected = true,
            onFavoriteIconClick = { },

            onRootClick = {},
            imageUrl = "",
            title = "RTX 4060 super duper magari umagresi video barati",
            condition = "New",
            price = "$1,780.00",
            location = "Located in Didi Dighomi"
        )
    }
}
