package com.tbc.search.domain.model.review.request

data class ReviewRequest(
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val reviewerUserName: String,
    val comment: String,
    val rating: String,
    val reviewAt: String,
    val title: String,
)