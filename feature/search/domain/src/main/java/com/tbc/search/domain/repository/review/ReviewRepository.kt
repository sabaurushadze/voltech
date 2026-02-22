package com.tbc.search.domain.repository.review

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.review.request.ReviewRequest
import com.tbc.search.domain.model.review.response.ReviewResponse

interface ReviewRepository {
    suspend fun getReviews(uid: String): Resource<List<ReviewResponse>, DataError.Network>

    suspend fun getLimitedReviews(uid: String, limit: Int): Resource<List<ReviewResponse>, DataError.Network>
    suspend fun getReviewsByReviewerUid(itemId: Int, uid: String): Resource<List<ReviewResponse>, DataError.Network>
    suspend fun addReview(reviewRequest: ReviewRequest): Resource<Unit, DataError.Network>
}