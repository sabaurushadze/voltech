package com.tbc.selling.domain.model

enum class Rating {
    POSITIVE,
    NEUTRAL,
    NEGATIVE;

    companion object {
        fun toServerString(rating: Rating): String {
            return when (rating) {
                POSITIVE -> "positive"
                NEUTRAL -> "neutral"
                NEGATIVE -> "negative"
            }
        }

        fun fromString(rating: String): Rating {
            return when (rating) {
                "positive" -> POSITIVE
                "neutral" -> NEUTRAL
                "negative" -> NEGATIVE
                else -> POSITIVE
            }
        }
    }
}