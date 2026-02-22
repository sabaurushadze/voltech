package com.tbc.search.presentation.screen.feedback

import com.tbc.search.presentation.enums.feedback.FeedbackFilterType
import com.tbc.search.presentation.enums.feedback.FeedbackSortType


sealed class FeedBackEvent {
    data class UpdateSellerUid(val sellerUid: String): FeedBackEvent()
    data class UpdateSelectedSortType(val sortType: FeedbackSortType): FeedBackEvent()
    data class UpdateSelectedFilterType(val sortType: FeedbackFilterType): FeedBackEvent()
    data object GetSeller: FeedBackEvent()
    data object GetReviews: FeedBackEvent()
    data object UpdateSortVisibilityStatus: FeedBackEvent()
    data object UpdateFilterVisibilityStatus: FeedBackEvent()
}