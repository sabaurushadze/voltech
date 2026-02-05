package com.tbc.search.domain.model.feed

import com.tbc.core.domain.model.category.Category

data class FeedItem(
    val id: Int,
    val title: String,
    val category: Category,
    val condition: Condition,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: Location,
    val userDescription: String,
    val sellerAvatar: String?,
    val sellerUserName: String,
)


enum class Condition {
    NEW, USED, PARTS;

    companion object {
        fun fromString(value: String): Condition {
            return when (value) {
                "NEW" -> NEW
                "USED" -> USED
                "PARTS" -> PARTS
                else -> NEW
            }
        }
    }

}

enum class Location {
    DIDI_DIGHOMI, GLDANI;

    companion object {
        fun fromString(value: String): Location {
            return when (value) {
                "DIDI_DIGHOMI" -> DIDI_DIGHOMI
                "GLDANI" -> GLDANI
                else -> DIDI_DIGHOMI
            }
        }
    }
}
