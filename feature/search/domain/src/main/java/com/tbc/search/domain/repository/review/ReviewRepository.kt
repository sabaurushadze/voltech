package com.tbc.search.domain.repository.review

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.review.ReviewResponse

interface ReviewRepository {
    suspend fun getReviews(uid: String): Resource<List<ReviewResponse>, DataError.Network>
}