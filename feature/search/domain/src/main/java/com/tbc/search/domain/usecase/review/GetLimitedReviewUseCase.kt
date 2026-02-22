package com.tbc.search.domain.usecase.review

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.review.response.ReviewResponse
import com.tbc.search.domain.repository.review.ReviewRepository
import javax.inject.Inject

class GetLimitedReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
){
    suspend operator fun invoke(uid: String, limit: Int) : Resource<List<ReviewResponse>, DataError.Network> {
        return repository.getLimitedReviews(uid, limit)
    }
}