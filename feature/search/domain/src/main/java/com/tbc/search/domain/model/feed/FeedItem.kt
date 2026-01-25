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
    GPU,
    CPU,
    MOTHERBOARD,
    RAM,
    SSD,
    HDD,
    CPU_COOLER,
    PSU,
    CASE_COOLER,
    CASE,
    MONITOR,
    CABEL,
}

enum class Condition {
    NEW,
    USED,
    PARTS,
}

enum class Location {
    DIDI_DIGHOMI,
    GLDANI,
}
