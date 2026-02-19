package com.tbc.search.domain.repository.review

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.review.ReviewRequest
import com.tbc.search.domain.model.review.ReviewResponse

interface ReviewRepository {
    suspend fun getReviews(uid: String): Resource<List<ReviewResponse>, DataError.Network>
    suspend fun getReviewsByReviewerUid(itemId: Int, uid: String): Resource<List<ReviewResponse>, DataError.Network>
    suspend fun addReview(reviewRequest: ReviewRequest): Resource<Unit, DataError.Network>
}