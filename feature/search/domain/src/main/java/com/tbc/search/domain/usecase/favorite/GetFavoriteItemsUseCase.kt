package com.tbc.search.domain.usecase.favorite

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.favorite.Favorite
import com.tbc.search.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class GetFavoriteItemsUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(uid: String): Resource<List<Favorite>, DataError.Network> {
        return favoriteRepository.getFavorites(uid)
    }
}