package com.tbc.search.domain.usecase.review

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.review.ReviewRequest
import com.tbc.search.domain.repository.review.ReviewRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val repository: ReviewRepository,
) {
    suspend operator fun invoke(reviewRequest: ReviewRequest): Resource<Unit, DataError.Network> {
        return repository.addReview(reviewRequest)
    }
}