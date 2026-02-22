package com.tbc.search.presentation.model.seller_profile.seller

data class UiSellerItem(
    val sellerName: String?,
    val sellerPhotoUrl: String?,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val positiveFeedback: Double = 100.0,
    val totalFeedback: Int = 0,
)