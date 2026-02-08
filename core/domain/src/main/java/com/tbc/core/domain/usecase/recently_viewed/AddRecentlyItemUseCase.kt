package com.tbc.core.domain.usecase.recently_viewed

import com.tbc.core.domain.model.recently_viewed.RecentlyRequest
import com.tbc.core.domain.repository.recently_viewed.RecentlyRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import javax.inject.Inject

class AddRecentlyItemUseCase @Inject constructor(
    private val repository: RecentlyRepository
){
    suspend operator fun invoke(recently: RecentlyRequest): Resource<Unit, DataError.Network>{
        return repository.addRecently(recently)
    }
}