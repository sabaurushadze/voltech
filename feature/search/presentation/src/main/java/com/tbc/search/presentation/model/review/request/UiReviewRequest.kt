package com.tbc.search.presentation.model.review.request

data class UiReviewRequest(
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val reviewerUserName: String,
    val comment: String,
    val rating: String,
    val title: String,
)