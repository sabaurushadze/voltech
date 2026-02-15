package com.tbc.core.data.remote.repository.recently_viewed

import com.tbc.core.data.remote.mapper.recently_viewed.toData
import com.tbc.core.data.remote.mapper.recently_viewed.toDomain
import com.tbc.core.data.remote.service.recently_viewed.RecentlyService
import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.core.domain.model.recently_viewed.RecentlyRequest
import com.tbc.core.domain.repository.recently_viewed.RecentlyRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import kotlinx.coroutines.delay
import javax.inject.Inject

class RecentlyRepositoryImpl @Inject constructor(
    private val recentlyService: RecentlyService,
    private val apiResponseHandler: ApiResponseHandler
): RecentlyRepository{

    override suspend fun getRecentlyViewed(uid: String): Resource<List<Recently>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            delay(500)
            recentlyService.getRecentlyViewed(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun getRecentlyViewedByQuantity(uid: String): Resource<List<Recently>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            delay(500)
            recentlyService.getRecentlyViewedByQuantity(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun addRecentlyViewed(recently: RecentlyRequest): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            recentlyService.addRecently(recently.toData())
        }
    }

    override suspend fun deleteRecentlyViewedById(id: Int): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            recentlyService.deleteRecentlyViewed(id)
        }
    }
}