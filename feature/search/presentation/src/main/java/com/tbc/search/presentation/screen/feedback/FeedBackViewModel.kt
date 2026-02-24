package com.tbc.search.presentation.screen.feedback

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.search.domain.usecase.item_details.CalculateSellerPositiveFeedbackUseCase
import com.tbc.search.domain.usecase.item_details.CalculateTotalFeedbackReceivedUseCase
import com.tbc.search.domain.usecase.review.GetReviewUseCase
import com.tbc.search.presentation.enums.feedback.FeedbackFilterType
import com.tbc.search.presentation.enums.feedback.FeedbackSortType
import com.tbc.search.presentation.mapper.review.toPresentation
import com.tbc.search.presentation.mapper.seller_profile.seller.toPresentation
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@HiltViewModel
class FeedBackViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
    private val getSellersUseCase: GetSellersUseCase,
    private val calculateSellerPositiveFeedback: CalculateSellerPositiveFeedbackUseCase,
    private val calculateTotalFeedbackReceived: CalculateTotalFeedbackReceivedUseCase,
): BaseViewModel<FeedBackState, Unit, FeedBackEvent>(FeedBackState()){

    override fun onEvent(event: FeedBackEvent) {
        when(event){
            is FeedBackEvent.UpdateSellerUid -> updateState { copy( sellerUid = event.sellerUid) }
            is FeedBackEvent.UpdateSelectedSortType -> {
                updateState { copy(selectedSortType = event.sortType) }
                sortReviewByDate()
                updateSortVisibilityStatus()
            }
            is FeedBackEvent.UpdateSelectedFilterType -> {
                updateState { copy(selectedFilterType = event.sortType) }
                filterReviewByRating()
                sortReviewByDate()
                updateFilterVisibilityStatus()
            }
            FeedBackEvent.UpdateSortVisibilityStatus ->  updateSortVisibilityStatus()
            FeedBackEvent.UpdateFilterVisibilityStatus -> updateFilterVisibilityStatus()
            FeedBackEvent.GetReviews -> getReviews()
            FeedBackEvent.GetSeller -> getSeller()
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun sortReviewByDate(){
        when(state.value.selectedSortType){
            FeedbackSortType.NEWEST -> {
                val sortedDesc = state.value.modifiedSellerReviewItems
                    .sortedByDescending {
                        Instant.parse(it.reviewAt)
                    }
                updateState { copy(modifiedSellerReviewItems = sortedDesc) }
            }
            FeedbackSortType.OLDEST -> {
                val sortedAsc = state.value.modifiedSellerReviewItems
                    .sortedBy {
                        Instant.parse(it.reviewAt)
                    }
                updateState { copy(modifiedSellerReviewItems = sortedAsc) }
            }
        }
    }

    private fun filterReviewByRating(){
        when(state.value.selectedFilterType){
            FeedbackFilterType.ALL_FEEDBACK -> {
                updateState{ copy(modifiedSellerReviewItems = state.value.sellerReviewItems)}
            }
            FeedbackFilterType.POSITIVE_FEEDBACK -> {
                val filteredList = state.value.sellerReviewItems.filter {
                    it.rating.toPresentation() == state.value.selectedFilterType
                }
                updateState { copy(modifiedSellerReviewItems = filteredList) }
            }
            FeedbackFilterType.NEUTRAL_FEEDBACK -> {
                val filteredList = state.value.sellerReviewItems.filter {
                    it.rating.toPresentation() == state.value.selectedFilterType
                }
                updateState { copy(modifiedSellerReviewItems = filteredList) }
            }
            FeedbackFilterType.NEGATIVE_FEEDBACK -> {
                val filteredList = state.value.sellerReviewItems.filter {
                    it.rating.toPresentation() == state.value.selectedFilterType
                }
                updateState { copy(modifiedSellerReviewItems = filteredList) }
            }
        }
    }

    private fun updateSortVisibilityStatus(){
        updateState { copy(isSortShow = !isSortShow) }
    }

    private fun updateFilterVisibilityStatus(){
        updateState { copy(isFilterShow = !isFilterShow) }
    }

    private fun getSeller() = viewModelScope.launch {
        updateState { copy(isSellerLoading = true) }

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
                            isSellerLoading = false,
                            showNoConnectionError = false
                        )
                    }
                }
            }
            .onFailure { result ->
                if (result == DataError.Network.NO_CONNECTION) {
                    updateState { copy(isSellerLoading = false, showNoConnectionError = true) }

                } else {
                    updateState { copy(isSellerLoading = false, showNoConnectionError = false) }
                }
            }
    }

    private fun getReviews(){
        viewModelScope.launch {
            updateState { copy(isReviewsLoading = true) }

            getReviewUseCase(uid = state.value.sellerUid)
                .onSuccess { sellerReviews ->
                    updateState {
                        copy(
                            sellerReviewItems = sellerReviews.toPresentation(),
                            isReviewsLoading = false,
                            showNoConnectionError = false
                        )
                    }
                    if(state.value.modifiedSellerReviewItems.isEmpty()){
                        updateState { copy(modifiedSellerReviewItems = sellerReviews.toPresentation()) }
                    }
                }
                .onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState { copy(isReviewsLoading = false, showNoConnectionError = true) }

                    } else {
                        updateState { copy(isReviewsLoading = false, showNoConnectionError = false) }
                    }
                }
        }
    }
}