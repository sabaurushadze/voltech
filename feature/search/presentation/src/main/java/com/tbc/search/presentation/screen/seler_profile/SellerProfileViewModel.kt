package com.tbc.search.presentation.screen.seler_profile

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.search.domain.usecase.feed.GetLimitedItemsByUidUseCase
import com.tbc.search.domain.usecase.item_details.CalculateSellerPositiveFeedbackUseCase
import com.tbc.search.domain.usecase.item_details.CalculateTotalFeedbackReceivedUseCase
import com.tbc.search.domain.usecase.review.GetLimitedReviewUseCase
import com.tbc.search.presentation.mapper.review.toPresentation
import com.tbc.search.presentation.mapper.seller_profile.seller.toPresentation
import com.tbc.search.presentation.mapper.seller_profile.seller_product.toPresentation
import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(
    private val getLimitedReviewUseCase: GetLimitedReviewUseCase,
    private val getSellersUseCase: GetSellersUseCase,
    private val getLimitedItemsByUidUseCase: GetLimitedItemsByUidUseCase,
    private val calculateSellerPositiveFeedback: CalculateSellerPositiveFeedbackUseCase,
    private val calculateTotalFeedbackReceived: CalculateTotalFeedbackReceivedUseCase,
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
                    updateState {
                        copy(
                            sellerProductItem = sellerProducts.toPresentation(),
                            isLoading = false,
                            showNoConnectionError = false
                        )
                    }
                }
                .onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState { copy(isLoading = false, showNoConnectionError = true) }

                    } else {
                        updateState { copy(isLoading = false, showNoConnectionError = false) }
                    }
                }
        }
    }

    private fun getSeller() = viewModelScope.launch {
        getSellersUseCase(state.value.sellerUid)
            .onSuccess { sellers ->
                val seller = sellers.toPresentation().firstOrNull()

                seller?.let { currentSeller ->

                    val positiveFeedback = calculateSellerPositiveFeedback(
                        positive = currentSeller.positive,
                        negative = currentSeller.negative
                    )

                    val totalFeedback = calculateTotalFeedbackReceived(
                        positive = currentSeller.positive,
                        neutral = currentSeller.neutral,
                        negative = currentSeller.negative
                    )

                    val updatedSeller = currentSeller.copy(
                        positiveFeedback = positiveFeedback,
                        totalFeedback = totalFeedback
                    )

                    updateState {
                        copy(
                            seller = updatedSeller,
                            isLoading = false,
                            showNoConnectionError = false
                        )
                    }
                }
            }
            .onFailure { result ->
                if (result == DataError.Network.NO_CONNECTION) {
                    updateState { copy(isLoading = false, showNoConnectionError = true) }

                } else {
                    updateState { copy(isLoading = false, showNoConnectionError = false) }
                }
            }
    }



    private fun getReviews(){
        viewModelScope.launch {
            getLimitedReviewUseCase(uid = state.value.sellerUid, limit = 4)
                .onSuccess { sellerReviews ->
                    updateState {
                        copy(
                            sellerReviewItems = sellerReviews.toPresentation(),
                            isLoading = false,
                            showNoConnectionError = false
                        )
                    }
                }
                .onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState { copy(isLoading = false, showNoConnectionError = true) }

                    } else {
                        updateState { copy(isLoading = false, showNoConnectionError = false) }
                    }
                }
        }
    }

    companion object{
        const val ITEMS_LIMIT = 4
    }
}