package com.tbc.search.presentation.mapper.review

import com.tbc.search.domain.model.review.ReviewRequest
import com.tbc.search.presentation.model.review.UiReviewRequest
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun UiReviewRequest.toDomain() =
    ReviewRequest(
        itemId = itemId,
        uid = uid,
        reviewerUid = reviewerUid,
        comment = comment,
        rating = rating,
        reviewAt = Clock.System.now().toString(),
        title = title
    )