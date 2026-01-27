package com.tbc.search.domain.model.feed

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
)

enum class Category {
    GPU, CPU, MOTHERBOARD, RAM, SSD, HDD, CPU_COOLER, PSU, CASE_COOLER, CASE, MONITOR, CABEL;

    companion object {
        fun fromString(value: String): Category {
            return when (value) {
                "GPU" -> GPU
                "CPU" -> CPU
                "MOTHERBOARD" -> MOTHERBOARD
                "RAM" -> RAM
                "SSD" -> SSD
                "HDD" -> HDD
                "CPU_COOLER" -> CPU_COOLER
                "PSU" -> PSU
                "CASE_COOLER" -> CASE_COOLER
                "CASE" -> CASE
                "MONITOR" -> MONITOR
                "CABEL" -> CABEL
                else -> GPU
            }
        }
    }
}

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
