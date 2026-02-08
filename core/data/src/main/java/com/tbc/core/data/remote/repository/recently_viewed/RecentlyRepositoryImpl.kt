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
import javax.inject.Inject

class RecentlyRepositoryImpl @Inject constructor(
    private val recentlyService: RecentlyService,
    private val responseHandler: ApiResponseHandler
): RecentlyRepository{
    override suspend fun getRecentlyViewed(uid: String): Resource<List<Recently>, DataError.Network> {
        return responseHandler.safeApiCall {
            recentlyService.getRecentlyViewed(uid)
        }.mapList { it.toDomain() }
    }



    override suspend fun addRecently(recently: RecentlyRequest): Resource<Unit, DataError.Network> {
        return responseHandler.safeApiCall {
            recentlyService.addRecently(recently.toData())
        }
    }
}