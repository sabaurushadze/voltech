package com.tbc.search.domain.usecase.favorite

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteItemByIdUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(id: Int): Resource<Unit, DataError.Network> {
        return favoriteRepository.deleteFavoriteById(id)
    }
}