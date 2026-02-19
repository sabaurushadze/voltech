package com.tbc.search.data.dto.review.request

import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequestDto(
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val comment: String,
    val rating: String,
    val reviewAt: String,
    val title: String,
)