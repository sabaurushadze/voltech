package com.tbc.search.presentation.screen.seler_profile

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.search.domain.usecase.feed.GetLimitedItemsByUidUseCase
import com.tbc.search.domain.usecase.review.GetReviewUseCase
import com.tbc.search.presentation.mapper.seller_profile.seller.toPresentation
import com.tbc.search.presentation.mapper.seller_profile.seller_product.toPresentation
import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
    private val getSellersUseCase: GetSellersUseCase,
    private val getLimitedItemsByUidUseCase: GetLimitedItemsByUidUseCase
) : BaseViewModel<SellerProfileState, Unit, SellerProfileEvent>(SellerProfileState()){


    override fun onEvent(event: SellerProfileEvent) {
        when(event){
            is SellerProfileEvent.SelectTab -> selectTab(event.tab)
            is SellerProfileEvent.UpdateSellerUid -> updateState { copy(sellerUid = event.sellerUid) }
            SellerProfileEvent.GetReviews -> getReviews()
            SellerProfileEvent.GetSeller -> getSeller()
            SellerProfileEvent.GetSellerLimitedItems -> getSellerLimitedItemByUid()
        }
    }

    private fun selectTab(tab: SellerProfileTab){
        updateState { copy(selectedTab = tab) }
    }

    private fun getSellerLimitedItemByUid() {
        viewModelScope.launch {
            getLimitedItemsByUidUseCase(state.value.sellerUid, ITEMS_LIMIT)
                .onSuccess { sellerProducts ->
                    updateState { copy(sellerProductItem = sellerProducts.toPresentation()) }
                }
        }
    }

    private fun getSeller(){
        viewModelScope.launch {
            getSellersUseCase(state.value.sellerUid)
                .onSuccess { seller ->
                    updateState { copy(seller = seller.toPresentation().firstOrNull()) }
                }
        }
    }

    private fun getReviews(){
        viewModelScope.launch {
            getReviewUseCase(state.value.sellerUid)
                .onSuccess {  }
        }
    }

    companion object{
        const val ITEMS_LIMIT = 4
    }
}