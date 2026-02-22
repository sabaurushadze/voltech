package com.tbc.search.data.service.review

import com.tbc.search.data.dto.review.request.ReviewRequestDto
import com.tbc.search.data.dto.review.response.ReviewResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface ReviewService {
    @GET(REVIEWS)
    suspend fun getReviews(
        @Query(UID) uid: String,
        @Query(SORT) sort: String = VIEWED_AT,
        @Query(ORDER) order: String = DESC,
    ): Response<List<ReviewResponseDto>>

    @GET(REVIEWS)
    suspend fun getLimitedReviews(
        @Query(UID) uid: String,
        @Query(SORT) sort: String = VIEWED_AT,
        @Query(ORDER) order: String = DESC,
        @Query(LIMIT) limit: Int
    ): Response<List<ReviewResponseDto>>

    @GET(REVIEWS)
    suspend fun getReviewsByReviewUid(
        @Query(REVIEWER_UID) uid: String,
        @Query(ITEM_ID) itemId: Int,
        @Query(SORT) sort: String = VIEWED_AT,
        @Query(ORDER) order: String = DESC,
    ): Response<List<ReviewResponseDto>>

    @POST(REVIEWS)
    suspend fun addReview(
        @Body reviewRequestDto: ReviewRequestDto
    ): Response<Unit>


    companion object{
        private const val REVIEWS = "reviews"
        private const val UID = "uid"
        private const val REVIEWER_UID = "reviewerUid"
        private const val ITEM_ID = "itemId"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val VIEWED_AT = "reviewAt"
        private const val DESC = "desc"
        private const val LIMIT = "_limit"
    }
}