package com.tbc.search.presentation.screen.feedback

import com.tbc.search.presentation.enums.feedback.FeedbackFilterType
import com.tbc.search.presentation.enums.feedback.FeedbackSortType
import com.tbc.search.presentation.model.review.response.UiReviewResponse
import com.tbc.search.presentation.model.seller_profile.seller.UiSellerItem

data class FeedBackState (
    val seller: UiSellerItem? = null,
    val isSellerLoading: Boolean = true,
    val isReviewsLoading: Boolean = true,

    val showNoConnectionError: Boolean = false,
    val sellerUid: String = "",
    val isSortShow: Boolean = false,
    val isFilterShow: Boolean = false,
    val sellerReviewItems: List<UiReviewResponse> = emptyList(),
    val modifiedSellerReviewItems: List<UiReviewResponse> = emptyList(),
    val selectedSortType: FeedbackSortType = FeedbackSortType.NEWEST,
    val selectedFilterType: FeedbackFilterType = FeedbackFilterType.ALL_FEEDBACK,
) {
    val isLoading: Boolean
        get() = isSellerLoading || isReviewsLoading
}