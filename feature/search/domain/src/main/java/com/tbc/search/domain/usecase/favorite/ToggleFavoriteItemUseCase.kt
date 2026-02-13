package com.tbc.search.domain.usecase.favorite

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.favorite.FavoriteRequestItem
import com.tbc.search.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteItemUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(favoriteItem: FavoriteRequestItem): Resource<Unit, DataError.Network>{
        return favoriteRepository.toggleFavorite(favoriteItem)
    }
}