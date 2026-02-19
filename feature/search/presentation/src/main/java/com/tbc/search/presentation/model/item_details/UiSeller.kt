package com.tbc.search.presentation.model.item_details

data class UiSeller(
    val id: Int,
    val uid: String,
    val sellerName: String?,
    val sellerPhotoUrl: String?,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val positiveFeedback: Double = 100.0,
    val totalFeedback: Int = 0,
)