package com.tbc.search.presentation.screen.seler_profile

import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab

sealed class SellerProfileEvent {
    data class UpdateSellerUid(val sellerUid: String): SellerProfileEvent()
    data class SelectTab(val tab: SellerProfileTab): SellerProfileEvent()
    data object GetReviews: SellerProfileEvent()
    data object GetSeller: SellerProfileEvent()
    data object GetSellerLimitedItems: SellerProfileEvent()
}