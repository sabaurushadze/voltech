package com.tbc.search.presentation.mapper.review


import com.tbc.search.domain.model.review.response.ReviewResponse
import com.tbc.search.presentation.model.review.response.UiReviewResponse

fun ReviewResponse.toPresentation() =
    UiReviewResponse(
        itemId = itemId,
        uid = uid,
        reviewerUid = reviewerUid,
        reviewerUserName = reviewerUserName,
        comment = comment,
        rating = rating,
        title = title,
        reviewAt = reviewAt
    )

fun List<ReviewResponse>.toPresentation() = this.map { it.toPresentation() }