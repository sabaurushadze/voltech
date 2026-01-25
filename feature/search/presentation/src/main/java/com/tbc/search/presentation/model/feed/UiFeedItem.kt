package com.tbc.search.presentation.model.feed

data class UiFeedItem(
    val id: Int,
    val title: String,
    val category: UiCategory,
    val condition: UiCondition,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: UiLocation,
    val userDescription: String,
)

enum class UiCategory {
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

enum class UiCondition {
    NEW,
    USED,
    PARTS,
}

enum class UiLocation {
    DIDI_DIGHOMI,
    GLDANI,
}
