package com.tbc.search.data.mapper.review

import com.tbc.core.domain.util.enumValueOfOrNull
import com.tbc.search.data.dto.review.response.ReviewResponseDto
import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.search.domain.model.review.response.ReviewResponse


internal fun ReviewResponseDto.toDomain() =
    ReviewResponse(
        id = id,
        itemId = itemId,
        uid = uid,
        reviewerUid = reviewerUid,
        reviewerUserName = reviewerUserName,
        comment = comment,
        rating = enumValueOfOrNull<ReviewRating>(rating) ?: ReviewRating.POSITIVE,
        reviewAt = reviewAt,
        title = title,
    )