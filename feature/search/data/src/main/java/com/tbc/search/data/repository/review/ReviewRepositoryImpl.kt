package com.tbc.search.data.repository.review

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.search.data.mapper.review.toDomain
import com.tbc.search.data.mapper.review.toDto
import com.tbc.search.data.service.review.ReviewService
import com.tbc.search.domain.model.review.ReviewRequest
import com.tbc.search.domain.model.review.ReviewResponse
import com.tbc.search.domain.repository.review.ReviewRepository
import javax.inject.Inject

internal class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val apiResponseHandler: ApiResponseHandler
): ReviewRepository {

    override suspend fun getReviews(uid: String): Resource<List<ReviewResponse>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            reviewService.getReviews(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun addReview(reviewRequest: ReviewRequest): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            reviewService.addReview(reviewRequest.toDto())
        }
    }

    override suspend fun getReviewsByReviewerUid(
        itemId: Int,
        uid: String,
    ): Resource<List<ReviewResponse>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            reviewService.getReviewsByReviewUid(itemId = itemId, uid = uid)
        }.mapList { it.toDomain() }
    }
}