package com.tbc.search.presentation.model.review.response

import com.tbc.search.domain.model.review.response.ReviewRating

data class UiReviewResponse(
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val reviewerUserName: String,
    val comment: String,
    val rating: ReviewRating,
    val title: String,
    val reviewAt: String,
)