package com.tbc.search.domain.model.review.response

data class ReviewResponse(
    val id: Int,
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val reviewerUserName: String,
    val comment: String,
    val rating: ReviewRating,
    val reviewAt: String,
    val title: String,
)