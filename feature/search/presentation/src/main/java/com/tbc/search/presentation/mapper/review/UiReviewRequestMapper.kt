package com.tbc.search.presentation.mapper.review

import com.tbc.search.domain.model.review.request.ReviewRequest
import com.tbc.search.presentation.model.review.request.UiReviewRequest
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun UiReviewRequest.toDomain() =
    ReviewRequest(
        itemId = itemId,
        uid = uid,
        reviewerUid = reviewerUid,
        reviewerUserName = reviewerUserName,
        comment = comment,
        rating = rating,
        reviewAt = Clock.System.now().toString(),
        title = title
    )