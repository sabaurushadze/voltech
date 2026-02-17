package com.tbc.search.data.dto.review

import kotlinx.serialization.Serializable

@Serializable
internal class ReviewResponseDto (
    val uid: String,
    val reviewerUid: String,
    val comment: String,
    val rating: String,
    val reviewAt: String,
)