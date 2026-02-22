package com.tbc.search.presentation.mapper.review

import com.tbc.resource.R
import com.tbc.search.presentation.enums.feedback.FeedbackSortType

fun FeedbackSortType.toStringRes(): Int {
    return when (this) {
        FeedbackSortType.NEWEST -> R.string.newsest_reviews
        FeedbackSortType.OLDEST -> R.string.oldest_reviews
    }
}