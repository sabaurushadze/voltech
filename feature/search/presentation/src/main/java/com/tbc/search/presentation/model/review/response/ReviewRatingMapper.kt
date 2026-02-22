package com.tbc.search.presentation.model.review.response

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.resource.R

@get:StringRes
val ReviewRating.textRes: Int
    get() = when (this) {
        ReviewRating.POSITIVE -> R.string.positive
        ReviewRating.NEGATIVE -> R.string.negative
        ReviewRating.NEUTRAL -> R.string.neutral
    }

@get:DrawableRes
val ReviewRating.iconRes: Int
    get() = when (this) {
        ReviewRating.POSITIVE -> R.drawable.ic_positive
        ReviewRating.NEGATIVE -> R.drawable.ic_negative
        ReviewRating.NEUTRAL -> R.drawable.ic_neutral
    }