package com.tbc.search.domain.model.feed

import com.tbc.core.domain.model.category.Category

data class FeedItem(
    val id: Int,
    val uid: String,
    val title: String,
    val category: Category,
    val condition: Condition,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: Location,
    val userDescription: String,
    val sellerName: String?,
    val sellerPhotoUrl: String?
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

        fun toServerString(condition: Condition): String {
            return when (condition) {
                NEW -> "NEW"
                USED -> "USED"
                PARTS -> "PARTS"
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

        fun toServerString(location: Location): String {
            return when (location) {
                DIDI_DIGHOMI -> "DIDI_DIGHOMI"
                GLDANI -> "GLDANI"
            }
        }
    }
}
