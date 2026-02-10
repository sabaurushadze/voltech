package com.tbc.core.domain.repository.recently_viewed

import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.core.domain.model.recently_viewed.RecentlyRequest
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface RecentlyRepository {
    suspend fun getRecentlyViewed(uid: String): Resource<List<Recently>, DataError.Network>

    suspend fun getRecentlyViewedByQuantity(uid: String): Resource<List<Recently>, DataError.Network>

    suspend fun addRecentlyViewed(recently: RecentlyRequest): Resource<Unit, DataError.Network>

    suspend fun deleteRecentlyViewedById(id: Int) : Resource<Unit, DataError.Network>

}