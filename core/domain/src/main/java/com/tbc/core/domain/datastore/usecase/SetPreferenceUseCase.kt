package com.tbc.core.domain.datastore.usecase

import androidx.datastore.preferences.core.Preferences
import com.tbc.core.domain.datastore.manager.DataStoreManager
import javax.inject.Inject

class SetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) {
        preferencesRepository.setPreference(key, value)
    }
}