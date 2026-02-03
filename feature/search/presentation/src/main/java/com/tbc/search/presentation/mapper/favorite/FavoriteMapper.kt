package com.tbc.search.presentation.mapper.favorite

import com.tbc.search.domain.model.favorite.Favorite
import com.tbc.search.presentation.model.favorite.UiFavorite

fun Favorite.toPresentation(): UiFavorite {
    return UiFavorite(
        itemId = itemId,
        id = id,
        uid = uid,
    )
}

fun UiFavorite.toDomain(): Favorite {
    return Favorite(
        id = id,
        uid = uid,
        itemId = itemId
    )
}