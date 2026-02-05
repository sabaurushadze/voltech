package com.tbc.core.domain.datastore.usecase

import androidx.datastore.preferences.core.Preferences
import com.tbc.core.domain.datastore.manager.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
) {
    operator fun <T> invoke(
        key: Preferences.Key<Set<T>>
    ): Flow<List<T>> =
        preferencesRepository.getSet(key)
}