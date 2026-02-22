package com.tbc.search.presentation.mapper.review

import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.search.presentation.enums.feedback.FeedbackFilterType

fun ReviewRating.toPresentation(): FeedbackFilterType {
    return when (this) {
        ReviewRating.POSITIVE -> FeedbackFilterType.POSITIVE_FEEDBACK
        ReviewRating.NEGATIVE -> FeedbackFilterType.NEGATIVE_FEEDBACK
        ReviewRating.NEUTRAL -> FeedbackFilterType.NEUTRAL_FEEDBACK
    }
}