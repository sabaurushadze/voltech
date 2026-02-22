package com.tbc.search.presentation.mapper.review

import com.tbc.resource.R
import com.tbc.search.presentation.enums.feedback.FeedbackFilterType

fun FeedbackFilterType.toStringRes(): Int {
    return when (this) {
        FeedbackFilterType.ALL_FEEDBACK -> R.string.all_feedback
        FeedbackFilterType.POSITIVE_FEEDBACK -> R.string.positive_feedback
        FeedbackFilterType.NEUTRAL_FEEDBACK -> R.string.neutral_feedback
        FeedbackFilterType.NEGATIVE_FEEDBACK -> R.string.negative_feedback
    }
}