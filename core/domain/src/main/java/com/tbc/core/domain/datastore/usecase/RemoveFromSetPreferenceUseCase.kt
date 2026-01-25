package com.tbc.core.domain.datastore.usecase

import androidx.datastore.preferences.core.Preferences
import com.tbc.core.domain.datastore.manager.DataStoreManager
import jakarta.inject.Inject

class RemoveFromSetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
) {
    suspend operator fun <T> invoke(
        key: Preferences.Key<Set<T>>,
        value: T
    ) {
        preferencesRepository.removeFromSet(key, value)
    }
}