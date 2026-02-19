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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.request.ImageRequest
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.button.CircleIconButton
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.button.SecondaryButton
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.image.ProfilePicturePlaceholder
import com.tbc.core_ui.components.pull_to_refresh.VoltechPullToRefresh
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.presentation.components.feed.items.FavoriteButton
import com.tbc.search.presentation.components.feed.sheet.ReviewBottomSheet
import com.tbc.selling.domain.model.Rating
import kotlinx.coroutines.flow.distinctUntilChanged
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    id: Int,
    viewModel: ItemDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToAddToCart: () -> Unit,
    navigateToSellerProfile: (String) -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pullToRefreshState = rememberPullToRefreshState()
    val reviewBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(ItemDetailsEvent.GetFavorites(state.user.uid))
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ItemDetailsEvent.GetCartItemIds)
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(id))
        viewModel.onEvent(ItemDetailsEvent.GetItemId(id))
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ItemDetailsEvent.AddRecentlyItem)
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

    VoltechPullToRefresh(
        state = pullToRefreshState,
        onRefresh = {
            viewModel.onEvent(ItemDetailsEvent.GetItemDetails(id))
            viewModel.onEvent(ItemDetailsEvent.GetCurrentSeller)

        },
    ) {
        ItemDetailsContent(
            state = state,
            onEvent = viewModel::onEvent,
            navigateToAddToCart = navigateToAddToCart,
            navigateToSellerProfile = navigateToSellerProfile
        )
    }


    if (state.showReviewSheet) {
        ModalBottomSheet(
            containerColor = VoltechColor.backgroundSecondary,
            onDismissRequest = {
                viewModel.onEvent(ItemDetailsEvent.HideReviewSheet)
                viewModel.onEvent(ItemDetailsEvent.ClearDescription)
                viewModel.onEvent(ItemDetailsEvent.ClearReviewErrors)
            },
            sheetState = reviewBottomSheetState
        ) {
            ReviewBottomSheet(
                options = listOf(Rating.POSITIVE, Rating.NEUTRAL, Rating.NEGATIVE),
                selectedSortType = state.selectedRating,
                state = state,
                onEvent = viewModel::onEvent,
            )
        }
    }

    state.previewStartIndex?.let { startIndex ->
        val images = state.itemDetails?.images ?: emptyList()

        val pagerState = rememberPagerState(
            initialPage = startIndex,
            pageCount = { images.size }
        )

        Dialog(
            onDismissRequest = { viewModel.onEvent(ItemDetailsEvent.CloseImagePreview) },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = pagerState,
                ) { page ->

                    ZoomableAsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(context)
                            .data(images[page])
                            .build(),
                        contentDescription = null,
                        alignment = Alignment.Center,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(Dimen.size8),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleIconButton(
                        modifier = Modifier
                            .statusBarsPadding(),
                        onClick = { viewModel.onEvent(ItemDetailsEvent.CloseImagePreview) },
                        iconRes = R.drawable.ic_remove_x,
                        iconColor = VoltechFixedColor.white,
                        backgroundColor = VoltechFixedColor.black.copy(0.9f),
                        size = Dimen.size16
                    )
                    CurrentPageOverlay(
                        listSize = images.size,
                        currentPosition = pagerState.currentPage + 1,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemDetailsContent(
    state: ItemDetailsState,
    onEvent: (ItemDetailsEvent) -> Unit,
    navigateToAddToCart: () -> Unit,
    navigateToSellerProfile: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(VoltechColor.backgroundPrimary)
            .systemBarsPadding()
    ) {


        state.itemDetails?.let { itemDetails ->
            val favoriteIds: List<Int> = state.favoriteItem.map { it.itemId }
            val isFavoriteItem: Boolean = itemDetails.id in favoriteIds

            if (!state.itemDetails.active) {
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = Dimen.size16),
                        text = stringResource(R.string.item_unavailable),
                        style = VoltechTextStyle.title2,
                        color = VoltechColor.foregroundPrimary,
                    )

                    Spacer(modifier = Modifier.height(Dimen.size24))
                }

            }

            with(itemDetails) {
                item {
                    ImagePager(
                        imagesList = images,
                        selectedImage = state.selectedImage,
                        onEvent = onEvent,
                        isFavoriteSelected = isFavoriteItem,
                        onFavoriteButtonIconClick = {
                            onEvent(ItemDetailsEvent.OnFavoriteToggle(uid = state.user.uid))
                        }
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
                            style = VoltechTextStyle.title2,
                            color = VoltechColor.foregroundPrimary,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.height(Dimen.size16))

                        state.seller?.let { seller ->
                            SellerItem(
                                sellerUid = itemDetails.uid,
                                navigateToSellerProfile = navigateToSellerProfile,
                                sellerAvatar = seller.sellerPhotoUrl,
                                sellerUserName = seller.sellerName,
                                sellerFeedbackAmount = seller.totalFeedback.toString(),
                                sellerPositiveFeedbackPercentage = seller.positiveFeedback.toString(),
                            )
                        }


                        Spacer(Modifier.height(Dimen.size16))

                        Text(
                            text = price,
                            style = VoltechTextStyle.title1,
                            color = VoltechColor.foregroundPrimary
                        )


                        if (state.itemDetails.active) {

                            Spacer(Modifier.height(Dimen.size32))

                            PrimaryButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = { onEvent(ItemDetailsEvent.BuyItem) },
                                text = stringResource(R.string.buy_it_now),
                            )

                            Spacer(Modifier.height(Dimen.size8))

                            SecondaryButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    if (!state.isInCart) {
                                        onEvent(ItemDetailsEvent.AddItemToCart)
                                    } else {
                                        navigateToAddToCart()
                                    }
                                },
                                text = if (state.isInCart) {
                                    stringResource(R.string.see_in_cart)
                                } else {
                                    stringResource(R.string.add_to_cart)
                                }
                            )



                            if (state.canGiveFeedback) {
                                Spacer(Modifier.height(Dimen.size8))

                                SecondaryButton(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        onEvent(ItemDetailsEvent.ShowReviewSheet)
                                    },
                                    text = stringResource(R.string.leave_a_review)
                                )

                            }

                        }
                        Spacer(Modifier.height(Dimen.size32))

                        ItemDescription(description = state.itemDetails.userDescription)

                        Spacer(Modifier.height(Dimen.size32))

                        AboutItem(
                            conditionRes = conditionRes,
                            quantity = quantity,
                            locationRes = locationRes,
                            categoryRes = categoryRes
                        )

                        Spacer(Modifier.height(Dimen.size32))
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemDescription(description: String) {
    Column {
        Text(
            text = stringResource(R.string.description),
            style = VoltechTextStyle.title2,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(Modifier.height(Dimen.size12))

        Text(
            text = description,
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundPrimary
        )
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
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundSecondary,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.weight(1.6f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = VoltechTextStyle.body,
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
    categoryRes: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(R.string.about_this_item),
            style = VoltechTextStyle.title2,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

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

        InfoRow(
            label = stringResource(R.string.category),
            value = stringResource(categoryRes)
        )
    }
}

@Composable
private fun SellerItem(
    sellerUid: String,
    sellerAvatar: String?,
    sellerUserName: String?,
    sellerFeedbackAmount: String,
    sellerPositiveFeedbackPercentage: String,
    navigateToSellerProfile: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToSellerProfile(sellerUid) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseAsyncImage(
            modifier = Modifier
                .size(Dimen.size48)
                .clip(CircleShape),
            url = sellerAvatar,
            fallback = {
                ProfilePicturePlaceholder(
                    text = sellerUserName,
                )
            }
        )

        Spacer(Modifier.width(Dimen.size8))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                sellerUserName?.let {
                    Text(
                        text = sellerUserName,
                        style = VoltechTextStyle.title3,
                        color = VoltechColor.foregroundPrimary
                    )
                }

                Spacer(modifier = Modifier.width(Dimen.size4))

                Text(
                    text = "(${sellerFeedbackAmount})",
                    style = VoltechTextStyle.bodyBold,
                    color = VoltechColor.foregroundPrimary
                )

            }

            Text(
                text = "$sellerPositiveFeedbackPercentage% positive feedback",
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}

@Composable
private fun ThumbnailBar(
    imagesList: List<String>,
    selectedImage: Int,
    onEvent: (ItemDetailsEvent) -> Unit,
) {

    val listState = rememberLazyListState()

    LaunchedEffect(selectedImage) {
        listState.animateScrollToItem(selectedImage)
    }

    LazyRow(
        state = listState,
        modifier = Modifier.padding(vertical = Dimen.size12),
        horizontalArrangement = Arrangement.spacedBy(Dimen.size8)
    ) {
        itemsIndexed(imagesList) { index, image ->

            Box(
                modifier = Modifier
                    .size(Dimen.size80)
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
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                vertical = Dimen.size8,
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
                style = VoltechTextStyle.body,
                color = VoltechFixedColor.black,
                modifier = Modifier
                    .clip(VoltechRadius.radius24)
            )

            Spacer(Modifier.width(Dimen.size2))

            Text(
                text = stringResource(R.string.of),
                style = VoltechTextStyle.body,
                color = VoltechFixedColor.black,
                modifier = Modifier
                    .clip(VoltechRadius.radius24)
            )

            Spacer(Modifier.width(Dimen.size2))

            Text(
                text = listSize.toString(),
                style = VoltechTextStyle.body,
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
    isFavoriteSelected: Boolean,
    onFavoriteButtonIconClick: () -> Unit,
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
                    onEvent(ItemDetailsEvent.SelectImageByIndex(page))
                }
        }

        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.size300)
                    .background(VoltechColor.backgroundPrimary)
                    .clickable { onEvent(ItemDetailsEvent.OpenImagePreview(page)) },
            ) {
                BaseAsyncImage(
                    contentScale = ContentScale.Fit,
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
            isSelected = isFavoriteSelected,
            onFavoriteIconClick = { onFavoriteButtonIconClick() },
            iconSize = Dimen.size24,
            iconContainerSize = Dimen.size48
        )
    }
}