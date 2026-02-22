package com.tbc.search.data.dto.review.response

import kotlinx.serialization.Serializable

@Serializable
internal class ReviewResponseDto (
    val id: Int,
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val reviewerUserName: String,
    val comment: String,
    val rating: String,
    val reviewAt: String,
    val title: String,
)