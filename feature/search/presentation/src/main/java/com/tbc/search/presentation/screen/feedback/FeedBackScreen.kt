package com.tbc.search.presentation.screen.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.util.toFormattedDate
import com.tbc.core_ui.components.button.PrimaryDoubleIconButton
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.image.ProfilePicturePlaceholder
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.pull_to_refresh.VoltechPullToRefresh
import com.tbc.core_ui.screen.empty_state.EmptyState
import com.tbc.core_ui.screen.internet.NoInternetConnection
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechBorder
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.presentation.components.feedback.sheet.filter.FeedbackFilterBottomSheet
import com.tbc.search.presentation.components.feedback.sheet.sort.FeedbackSortBottomSheet
import com.tbc.search.presentation.enums.feedback.FeedbackFilterType
import com.tbc.search.presentation.enums.feedback.FeedbackSortType
import com.tbc.search.presentation.model.review.response.UiReviewResponse
import com.tbc.search.presentation.model.review.response.iconRes
import com.tbc.search.presentation.model.seller_profile.seller.UiSellerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBackScreen(
    sellerUid: String,
    viewModel: FeedBackViewModel = hiltViewModel(),
    navigateToItemDetails: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val sortBottomSheetState = rememberModalBottomSheetState()
    val pullToRefreshState = rememberPullToRefreshState()


    LaunchedEffect(Unit) {
        onEvent(FeedBackEvent.UpdateSellerUid(sellerUid))
        onEvent(FeedBackEvent.GetSeller)
    }

    LaunchedEffect(Unit) {
        onEvent(FeedBackEvent.GetReviews)
    }

    if (state.isFilterShow) {
        ModalBottomSheet(
            containerColor = VoltechColor.backgroundSecondary,
            onDismissRequest = { onEvent(FeedBackEvent.UpdateFilterVisibilityStatus) },
            sheetState = sortBottomSheetState
        ) {
            FeedbackFilterBottomSheet(
                options = FeedbackFilterType.entries,
                selectedSortType = state.selectedFilterType,
                onItemClick = { filterType ->
                    onEvent(FeedBackEvent.UpdateSelectedFilterType(filterType))
                }
            )
        }
    }

    if (state.isSortShow) {
        ModalBottomSheet(
            containerColor = VoltechColor.backgroundSecondary,
            onDismissRequest = { onEvent(FeedBackEvent.UpdateSortVisibilityStatus) },
            sheetState = sortBottomSheetState
        ) {
            FeedbackSortBottomSheet(
                options = listOf(FeedbackSortType.NEWEST, FeedbackSortType.OLDEST),
                selectedSortType = state.selectedSortType,
                onItemClick = { sortType ->
                    onEvent(FeedBackEvent.UpdateSelectedSortType(sortType))
                }
            )
        }
    }

    if (state.showNoConnectionError) {
        NoInternetConnection {
            onEvent(FeedBackEvent.GetSeller)
        }
    } else if (state.isLoading) {
        LoadingScreen()
    } else if (state.sellerReviewItems.isEmpty()) {
        EmptyState(
            title = stringResource(R.string.no_review),
            icon = R.drawable.ic_review
        )
    } else {
        VoltechPullToRefresh(
            state = pullToRefreshState,
            onRefresh = {
                onEvent(FeedBackEvent.GetSeller)
            },
        ) {
            FeedBackContent(
                state = state,
                onEvent = onEvent,
                navigateToItemDetails = navigateToItemDetails,
            )
        }
    }
}


@Composable
private fun FeedBackContent(
    state: FeedBackState,
    onEvent: (FeedBackEvent) -> Unit,
    navigateToItemDetails: (Int) -> Unit,
) = with(state) {

    Column {

        seller?.let { sellerInfo ->
            SellerItem(sellerInfo)

            Spacer(Modifier.height(Dimen.size24))

            SellerFeedBack(sellerInfo.totalFeedback)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimen.size16),
                contentPadding = PaddingValues(bottom = Dimen.size80, top = Dimen.size24)
            ) {
                items(state.modifiedSellerReviewItems) { sellerReview ->
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
            }

            PrimaryDoubleIconButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Dimen.size16),
                leftText = stringResource(R.string.sort),
                rightText = stringResource(R.string.filter),
                leftIcon = R.drawable.ic_sort,
                rightIcon = R.drawable.ic_filter,
                leftOnClick = { onEvent(FeedBackEvent.UpdateSortVisibilityStatus) },
                rightOnClick = { onEvent(FeedBackEvent.UpdateFilterVisibilityStatus) },
            )
        }
    }

}

@Composable
private fun SellerReviewItem(
    review: UiReviewResponse,
    navigateToItemDetails: (Int) -> Unit,
) = with(review) {
    Column {
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
            ) {}

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
                .clickable { navigateToItemDetails(itemId) }
        )
    }
}

@Composable
private fun SellerFeedBack(
    totalFeedback: Int,
) {
    Column(
        modifier = Modifier.padding(horizontal = Dimen.size16)
    ) {
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
private fun SellerItem(
    sellerInfo: UiSellerItem,
) = with(sellerInfo) {
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