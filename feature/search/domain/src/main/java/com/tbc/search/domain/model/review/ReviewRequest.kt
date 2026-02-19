package com.tbc.search.domain.model.review

data class ReviewRequest(
    val itemId: Int,
    val uid: String,
    val reviewerUid: String,
    val comment: String,
    val rating: String,
    val reviewAt: String,
    val title: String,
)