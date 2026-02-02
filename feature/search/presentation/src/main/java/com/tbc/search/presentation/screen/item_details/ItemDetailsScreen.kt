package com.tbc.search.presentation.screen.item_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.button.AppOutlinedButton
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.core_ui.theme.VoltechTheme
import com.tbc.resource.R
import com.tbc.search.presentation.components.feed.items.FavoriteButton
import com.tbc.search.presentation.components.feed.items.FeedItemCard
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    id: Int,
    viewModel: ItemDetailsViewModel = hiltViewModel(),
    onSetupTopBar: (TopBarState) -> Unit,
    navigateBack: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetupTopBar(onSetupTopBar, viewModel::onEvent)

    LaunchedEffect(Unit) {
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(id))
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ItemDetailsSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            ItemDetailsSideEffect.NavigateBackToFeed -> {
                navigateBack()
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


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(VoltechColor.backgroundPrimary)
            .systemBarsPadding()
    ) {

        state.itemDetails?.let { itemDetails ->
            with(itemDetails) {
                item {
                    ImagePager(
                        imagesList = images,
                        selectedImage = state.selectedImage,
                        onEvent = onEvent,
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimen.size16)
                    ) {
                        ThumbnailBar(
                            imagesList = images,
                            selectedImage = state.selectedImage,
                            onEvent = onEvent,
                        )

                        Spacer(Modifier.height(Dimen.size12))

                        Text(
                            text = title,
                            style = VoltechTextStyle.title21Bold,
                            color = VoltechColor.foregroundPrimary,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.height(Dimen.size16))

                        SellerItem(
                            sellerAvatar = sellerAvatar,
                            sellerUerName = sellerUserName
                        )

                        Spacer(Modifier.height(Dimen.size16))

                        Text(
                            text = price,
                            style = VoltechTextStyle.title24Bold,
                            color = VoltechColor.foregroundPrimary
                        )

                        Spacer(Modifier.height(Dimen.size24))

                        AppOutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimen.size48)
                                .clip(VoltechRadius.radius24),
                            onClick = {},
                            text = stringResource(R.string.buy_it_now),
                            textColor = VoltechFixedColor.white,
                            textStyle = VoltechTextStyle.body16Bold,
                            backgroundColor = VoltechFixedColor.blue,
                            borderColor = VoltechFixedColor.blue
                        )

                        Spacer(Modifier.height(Dimen.size10))

                        AppOutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimen.size48)
                                .clip(VoltechRadius.radius24),
                            onClick = {},
                            text = stringResource(R.string.add_to_cart),
                            textColor = VoltechFixedColor.blue,
                            textStyle = VoltechTextStyle.body16Normal,
                            backgroundColor = VoltechFixedColor.transparent,
                            borderColor = VoltechFixedColor.blue
                        )

                        Spacer(Modifier.height(Dimen.size10))

                        AppOutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimen.size48)
                                .clip(VoltechRadius.radius24),
                            onClick = {},
                            text = stringResource(R.string.add_to_watchlist),
                            textColor = VoltechFixedColor.blue,
                            textStyle = VoltechTextStyle.body16Normal,
                            backgroundColor = VoltechFixedColor.transparent,
                            borderColor = VoltechFixedColor.blue
                        )

                        Spacer(Modifier.height(Dimen.size24))

                        AboutItem(
                            conditionRes = conditionRes,
                            quantity = quantity,
                            locationRes = locationRes
                        )

                        Spacer(Modifier.height(Dimen.size16))
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.size6),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = VoltechTextStyle.body16Normal,
            color = VoltechColor.backgroundSecondary,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.weight(1.6f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = VoltechTextStyle.body16Bold,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}


@Composable
private fun AboutItem(
    conditionRes: Int,
    quantity: String,
    locationRes: Int,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(R.string.about_this_item),
            style = VoltechTextStyle.body22Bold,
            color = VoltechColor.foregroundPrimary
        )

        InfoRow(
            label = stringResource(R.string.condition),
            value = stringResource(conditionRes)
        )

        InfoRow(
            label = stringResource(R.string.quantity),
            value = stringResource(R.string.item_available, quantity)
        )

        InfoRow(
            label = stringResource(R.string.location),
            value = stringResource(locationRes)
        )
    }
}

@Composable
private fun SellerItem(
    sellerAvatar: String?,
    sellerUerName: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        sellerAvatar?.let {
            BaseAsyncImage(
                url = sellerAvatar,
                modifier = Modifier
                    .size(Dimen.size48)
                    .clip(CircleShape)
            )
        }

        Spacer(Modifier.width(Dimen.size10))

        Text(
            text = sellerUerName,
            style = VoltechTextStyle.body16Bold,
            color = VoltechColor.foregroundPrimary
        )
    }
}

@Composable
private fun ThumbnailBar(
    imagesList: List<String>,
    selectedImage: Int,
    onEvent: (ItemDetailsEvent) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(
            vertical = Dimen.size12
        ),
        horizontalArrangement = Arrangement.spacedBy(Dimen.size10)
    ) {
        itemsIndexed(imagesList) { index, image ->

            Box(
                modifier = Modifier
                    .size(Dimen.size82)
                    .clickable {
                        onEvent(ItemDetailsEvent.SelectImageByIndex(index))
                    }
            ) {

                BaseAsyncImage(
                    url = image,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimen.size6)
                        .clip(VoltechRadius.radius8)
                        .background(VoltechFixedColor.lightGray)

                )

                if (selectedImage == index) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = Dimen.size2,
                                color = VoltechColor.foregroundPrimary,
                                shape = VoltechRadius.radius12
                            )
                    )
                }

            }

        }
    }
}

@Composable
private fun CurrentPageOverlay(
    listSize: Int,
    currentPosition: Int,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                vertical = Dimen.size10,
                horizontal = Dimen.size16
            )
            .clip(VoltechRadius.radius12)
            .background(VoltechFixedColor.white.copy(0.7f))

    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Dimen.size6,
                    vertical = Dimen.size4
                ),
        ) {
            Text(
                text = currentPosition.toString(),
                style = VoltechTextStyle.body14Bold,
                color = VoltechFixedColor.black,
                modifier = Modifier
                    .clip(VoltechRadius.radius24)
            )

            Spacer(Modifier.width(Dimen.size2))

            Text(
                text = stringResource(R.string.of),
                style = VoltechTextStyle.body14Bold,
                color = VoltechFixedColor.black,
                modifier = Modifier
                    .clip(VoltechRadius.radius24)
            )

            Spacer(Modifier.width(Dimen.size2))

            Text(
                text = listSize.toString(),
                style = VoltechTextStyle.body14Bold,
                color = VoltechFixedColor.black,
                modifier = Modifier
                    .clip(VoltechRadius.radius24)
            )
        }
    }
}

@Composable
private fun ImagePager(
    imagesList: List<String>,
    selectedImage: Int,
    onEvent: (ItemDetailsEvent) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { imagesList.size },
        initialPage = selectedImage
    )

    Box {
        val currentPosition by remember {
            derivedStateOf { pagerState.currentPage + 1 }
        }

        LaunchedEffect(selectedImage) {
            if (selectedImage in imagesList.indices &&
                selectedImage != pagerState.currentPage
            ) {
                pagerState.scrollToPage(selectedImage)
            }
        }

        LaunchedEffect(Unit) {
            snapshotFlow { pagerState.currentPage }
                .distinctUntilChanged()
                .collect { page ->
                    println(page)
                    onEvent(ItemDetailsEvent.SelectImageByIndex(page))
                }
        }

        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.size300)
                    .background(VoltechFixedColor.white),
            ) {
                BaseAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    url = imagesList[page]
                )
            }
        }

        CurrentPageOverlay(
            listSize = imagesList.size,
            currentPosition = currentPosition,
            modifier = Modifier.align(Alignment.TopEnd),
        )

        FavoriteButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimen.size24),
            isSelected = false,
            onFavoriteIconClick = { },
            iconSize = Dimen.size24
        )
    }
}

@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    onEvent: (ItemDetailsEvent) -> Unit,
) {
    val title = stringResource(id = R.string.item)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = { onEvent(ItemDetailsEvent.NavigateBackToFeed) }
                )
            )
        )
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
