package com.tbc.search.data.mapper.review

import com.tbc.search.data.dto.review.ReviewResponseDto
import com.tbc.search.domain.model.review.ReviewResponse


internal fun ReviewResponseDto.toDomain() =
    ReviewResponse(
        uid = uid,
        reviewerUid = reviewerUid,
        comment = comment,
        rating = rating,
        reviewAt = reviewAt
    )