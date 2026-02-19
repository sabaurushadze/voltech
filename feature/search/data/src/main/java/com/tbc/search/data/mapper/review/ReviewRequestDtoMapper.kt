package com.tbc.search.data.mapper.review

import com.tbc.search.data.dto.review.request.ReviewRequestDto
import com.tbc.search.domain.model.review.ReviewRequest

fun ReviewRequest.toDto() =
    ReviewRequestDto(
        itemId = itemId,
        uid = uid,
        reviewerUid = reviewerUid,
        comment = comment,
        rating = rating,
        reviewAt = reviewAt,
        title = title
    )