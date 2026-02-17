package com.tbc.search.presentation.seler_profile

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.search.domain.usecase.review.GetReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(
    private val getReviewUseCase: GetReviewUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel<SellerProfileState, Unit, SellerProfileEvent>(SellerProfileState()){

    init {
        getReviews()
    }

    private fun getReviews(){
        viewModelScope.launch {
            val user = getCurrentUserUseCase()

            user?.let { user ->
                getReviewUseCase(user.uid)
                    .onSuccess {
                        println("Mushaobs dmzao $it")
                    }
                    .onFailure {
                        println("Ar Mushaobs dmzao $it")
                    }
            }
        }
    }
}