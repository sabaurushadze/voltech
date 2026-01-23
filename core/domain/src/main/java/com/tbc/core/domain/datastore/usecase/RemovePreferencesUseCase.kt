package com.tbc.core.domain.datastore.usecase

import androidx.datastore.preferences.core.Preferences
import com.tbc.core.domain.datastore.manager.DataStoreManager
import javax.inject.Inject

class RemovePreferencesUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
)  {
    suspend operator fun invoke(keys: List<Preferences.Key<*>>) {
        preferencesRepository.removePreferences(keys)
    }
}