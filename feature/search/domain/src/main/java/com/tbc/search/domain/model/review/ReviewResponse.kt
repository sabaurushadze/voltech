package com.tbc.search.domain.model.review

data class ReviewResponse(
    val uid: String,
    val reviewerUid: String,
    val comment: String,
    val rating: String,
    val reviewAt: String
)