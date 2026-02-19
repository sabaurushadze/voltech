package com.tbc.search.presentation.model.review

data class UiReviewRequest(
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val comment: String,
    val rating: String,
    val title: String,
)