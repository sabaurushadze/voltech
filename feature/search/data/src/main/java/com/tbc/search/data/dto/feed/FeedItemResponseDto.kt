package com.tbc.search.data.dto.feed

import kotlinx.serialization.Serializable

@Serializable
data class FeedItemResponseDto(
    val id: Int,
    val title: String,
    val category: CategoryDto,
    val condition: ConditionDto,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: LocationDto,
    val userDescription: String,
)

@Serializable
enum class CategoryDto {
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

@Serializable
enum class ConditionDto {
    NEW,
    USED,
    PARTS,
}

@Serializable
enum class LocationDto {
    DIDI_DIGHOMI,
    GLDANI,
}