package com.tbc.search.presentation.screen.seler_profile

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.util.toFormattedDate
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.image.ProfilePicturePlaceholder
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.pull_to_refresh.VoltechPullToRefresh
import com.tbc.core_ui.screen.empty_state.EmptyState
import com.tbc.core_ui.screen.internet.NoInternetConnection
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.search.presentation.model.review.response.UiReviewResponse
import com.tbc.search.presentation.model.review.response.iconRes
import com.tbc.search.presentation.model.review.response.textRes
import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab
import com.tbc.search.presentation.model.seller_profile.seller.UiSellerItem
import com.tbc.search.presentation.model.seller_profile.seller_product.UiSellerProductItem

@Composable
fun SellerProfileScreen(
    viewModel: SellerProfileViewModel = hiltViewModel(),
    sellerUid: String,
    navigateToFeedWithUid: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToFeedback: (String) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        onEvent(SellerProfileEvent.UpdateSellerUid(sellerUid))
        onEvent(SellerProfileEvent.GetSeller)
    }

    LaunchedEffect(Unit) {
        onEvent(SellerProfileEvent.GetReviews)
    }

    LaunchedEffect(Unit) {
        onEvent(SellerProfileEvent.GetSellerLimitedItems)
    }



    if (state.showNoConnectionError) {
        NoInternetConnection {
            onEvent(SellerProfileEvent.GetSeller)
            onEvent(SellerProfileEvent.GetReviews)
            onEvent(SellerProfileEvent.GetSellerLimitedItems)
        }
    } else if (state.isLoading) {
        LoadingScreen()
    }  else {
        VoltechPullToRefresh(
            state = pullToRefreshState,
            onRefresh = {
                onEvent(SellerProfileEvent.GetSeller)
                onEvent(SellerProfileEvent.GetReviews)
                onEvent(SellerProfileEvent.GetSellerLimitedItems)
            },
        ) {
            SellerProfileContent(
                state = state,
                onEvent = onEvent,
                navigateToFeedWithUid = navigateToFeedWithUid,
                navigateToItemDetails = navigateToItemDetails,
                navigateToFeedback = navigateToFeedback
            )
        }
    }
}

@Composable
private fun SellerProfileContent(
    state: SellerProfileState,
    navigateToFeedWithUid: (String) -> Unit,
    onEvent: (SellerProfileEvent) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToFeedback: (String) -> Unit
){
    TopBarNavigation(
        state = state,
        onEvent = onEvent,
        navigateToFeedWithUid = navigateToFeedWithUid,
        navigateToItemDetails = navigateToItemDetails,
        navigateToFeedback = navigateToFeedback
    )
}

@Composable
private fun TopBarNavigation(
    state: SellerProfileState,
    onEvent: (SellerProfileEvent) -> Unit,
    navigateToFeedWithUid: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    navigateToFeedback: (String) -> Unit
) {
    val tabs = SellerProfileTab.entries
    val pagerState = rememberPagerState { tabs.size }

    LaunchedEffect(state.selectedTab) {
        val index = state.selectedTab.ordinal
        if (pagerState.currentPage != index) {
            pagerState.animateScrollToPage(index)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val currentTab = tabs[pagerState.currentPage]
        if (state.selectedTab != currentTab) {
            onEvent(SellerProfileEvent.SelectTab(currentTab))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.seller?.let {
            SellerItem(state.seller)
        }

        PrimaryTabRow(
            selectedTabIndex = state.selectedTab.ordinal,
            contentColor = VoltechColor.foregroundPrimary,
            containerColor = VoltechColor.backgroundPrimary,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectedTabIndex = state.selectedTab.ordinal
                    ),
                    color = VoltechColor.foregroundAccent
                )
            }
        ) {
            tabs.forEach { tab ->
                val isSelected = state.selectedTab == tab
                Tab(
                    selected = state.selectedTab == tab,
                    onClick = {
                        onEvent(SellerProfileEvent.SelectTab(tab))
                    },
                    text = {
                        Text(
                            text = tab.title,
                            style = if (isSelected){
                                VoltechTextStyle.title3
                            }else{
                                VoltechTextStyle.body
                            }
                        )
                    },
                    selectedContentColor = VoltechColor.foregroundPrimary,
                    unselectedContentColor = VoltechColor.foregroundSecondary
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            when (tabs[page]) {
                SellerProfileTab.STORE -> {
                    StoreContent(
                        state = state,
                        navigateToFeedWithUid = navigateToFeedWithUid,
                        navigateToItemDetails = navigateToItemDetails
                    )
                }

                SellerProfileTab.REVIEWS -> {
                    if (state.sellerReviewItems.isEmpty()){
                        EmptyState(
                            title = stringResource(R.string.no_review),
                            icon = R.drawable.ic_review
                        )
                    }else{
                        ReviewsContent(
                            state = state,
                            navigateToItemDetails = navigateToItemDetails,
                            navigateToFeedback = navigateToFeedback
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun ReviewsContent(
    state: SellerProfileState,
    navigateToItemDetails: (Int) -> Unit,
    navigateToFeedback: (String) -> Unit
) = with(state){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.size16),
    ) {
        item {
            seller?.let {
                FeedBackRating(
                    positive = seller.positive,
                    neutral = seller.neutral,
                    negative = seller.negative
                )

                Spacer(Modifier.height(Dimen.size32))

                SellerFeedBack(seller.totalFeedback)
            }
        }

        items(state.sellerReviewItems){ sellerReview ->
            SellerReviewItem(
                review = sellerReview,
                navigateToItemDetails = navigateToItemDetails
            )

            Spacer(Modifier.height(Dimen.size16))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = VoltechBorder.medium,
                color = VoltechColor.borderSubtle
            )

            Spacer(Modifier.height(Dimen.size16))
        }

        item {
            Spacer(Modifier.height(Dimen.size16))

            if (state.sellerReviewItems.size >= 4){
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.see_all),
                    onClick = { navigateToFeedback(sellerUid) }
                )
            }

            Spacer(Modifier.height(Dimen.size16))
        }
    }
}

@Composable
private fun SellerFeedBack(
    totalFeedback: Int
){
    Column {
        Row {
            Text(
                text = stringResource(R.string.seller_feedback),
                style = VoltechTextStyle.title2,
                color = VoltechColor.foregroundPrimary
            )

            Spacer(Modifier.width(Dimen.size8))

            Text(
                text = "(${totalFeedback})",
                style = VoltechTextStyle.title2,
                color = VoltechColor.foregroundSecondary
            )
        }

        Spacer(Modifier.height(Dimen.size16))
    }
}

@Composable
private fun SellerReviewItem(
    review: UiReviewResponse,
    navigateToItemDetails: (Int) -> Unit,
) = with(review){
    Column{
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(review.rating.iconRes),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(Modifier.width(Dimen.size8))

            Text(
                text = reviewerUserName,
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundSecondary
            )

            Spacer(Modifier.width(Dimen.size8))

            Box(
                modifier = Modifier
                    .size(Dimen.size4)
                    .clip(CircleShape)
                    .background(VoltechColor.foregroundSecondary)
            ){}

            Spacer(Modifier.width(Dimen.size8))

            Text(
                text = reviewAt.toFormattedDate(),
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundSecondary
            )
        }

        Spacer(Modifier.height(Dimen.size12))

        Text(
            text = comment,
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(Modifier.height(Dimen.size8))

        Text(
            text = title,
            style = VoltechTextStyle.bodyUnderLine,
            color = VoltechColor.foregroundSecondary,
            modifier = Modifier
                .clickable{ navigateToItemDetails(itemId) }
        )
    }
}

@Composable
private fun FeedBackRating(
    positive: Int,
    neutral: Int,
    negative: Int
){
    Spacer(Modifier.height(Dimen.size24))

    Text(
        text = stringResource(R.string.feedback_rating),
        style = VoltechTextStyle.title2,
        color = VoltechColor.foregroundPrimary
    )

    Spacer(Modifier.height(Dimen.size16))

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(ReviewRating.entries){ rating ->
            Column (modifier = Modifier.padding(end = Dimen.size48)) {
                Text(
                    text = stringResource(rating.textRes),
                    style = VoltechTextStyle.bodyBold,
                    color = VoltechColor.foregroundPrimary
                )

                Spacer(Modifier.height(Dimen.size12))

                Text(
                    text = when (rating) {
                        ReviewRating.POSITIVE -> positive.toString()
                        ReviewRating.NEGATIVE -> negative.toString()
                        ReviewRating.NEUTRAL -> neutral.toString()
                    },
                    style = VoltechTextStyle.body,
                    color = VoltechColor.foregroundPrimary
                )
            }
        }
    }
}


@Composable
private fun StoreContent(
    state: SellerProfileState,
    navigateToFeedWithUid: (String) -> Unit,
    navigateToItemDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.size16),
        contentPadding = PaddingValues(vertical = Dimen.size16),
        horizontalArrangement = Arrangement.spacedBy(Dimen.size12),
        verticalArrangement = Arrangement.spacedBy(Dimen.size12)
    ) {
        item(span = { GridItemSpan(2) }) {
            Spacer(Modifier.height(Dimen.size16))

            Text(
                text = stringResource(R.string.latest),
                style = VoltechTextStyle.title2,
                color = VoltechColor.foregroundPrimary
            )
        }

        items(state.sellerProductItem) { sellerProduct ->
            SellerProductItem(
                sellerProductItem = sellerProduct,
                navigateToItemDetails = navigateToItemDetails
            )
        }

        if (state.sellerProductItem.size >= 4) {
            item(span = { GridItemSpan(2) }) {
                Spacer(Modifier.height(Dimen.size24))

                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.see_all),
                    onClick = { navigateToFeedWithUid(state.sellerUid) }
                )

                Spacer(Modifier.height(Dimen.size12))
            }
        }
    }
}

@Composable
private fun SellerProductItem(
    sellerProductItem: UiSellerProductItem,
    navigateToItemDetails: (Int) -> Unit,
) = with(sellerProductItem) {
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
private fun SellerItem(
    sellerInfo: UiSellerItem
) = with(sellerInfo){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(VoltechColor.backgroundPrimary)
            .padding(all = Dimen.size16),
            verticalAlignment = Alignment.CenterVertically
    ) {
        BaseAsyncImage(
            modifier = Modifier
                .size(Dimen.size48)
                .clip(CircleShape),
            url = sellerPhotoUrl,
            fallback = {
                ProfilePicturePlaceholder(
                    text = sellerName,
                )
            }
        )

        Spacer(Modifier.width(Dimen.size8))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                sellerName?.let {
                    Text(
                        text = sellerName,
                        style = VoltechTextStyle.title3,
                        color = VoltechColor.foregroundPrimary
                    )
                }

                Spacer(modifier = Modifier.width(Dimen.size4))

                Text(
                    text = "(${totalFeedback})",
                    style = VoltechTextStyle.bodyBold,
                    color = VoltechColor.foregroundPrimary
                )

            }

            Text(
                text = "$positiveFeedback% positive feedback",
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}