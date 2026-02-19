package com.tbc.search.presentation.screen.seler_profile

import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab
import com.tbc.search.presentation.model.seller_profile.seller.UiSellerItem
import com.tbc.search.presentation.model.seller_profile.seller_product.UiSellerProductItem

data class SellerProfileState (
    val seller: UiSellerItem? = null,
    val sellerUid: String = "",
    val selectedTab: SellerProfileTab = SellerProfileTab.STORE,
    val sellerProductItem: List<UiSellerProductItem> = emptyList(),
)