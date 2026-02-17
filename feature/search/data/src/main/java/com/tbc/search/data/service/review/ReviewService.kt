package com.tbc.search.data.service.review

import com.tbc.search.data.dto.review.ReviewResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ReviewService {
    @GET(REVIEWS)
    suspend fun getReviews(
        @Query(UID) uid: String,
        @Query(SORT) sort: String = VIEWED_AT,
        @Query(ORDER) order: String = DESC,
    ): Response<List<ReviewResponseDto>>

    companion object{
        private const val REVIEWS = "reviews"
        private const val UID = "uid"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val VIEWED_AT = "reviewAt"
        private const val DESC = "desc"
    }
}