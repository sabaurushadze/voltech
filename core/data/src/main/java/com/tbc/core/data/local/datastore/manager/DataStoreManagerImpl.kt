package com.tbc.core.data.local.datastore.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.tbc.core.domain.datastore.manager.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreManager {
    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map { preferences -> preferences[key] ?: defaultValue }
    }

    override suspend fun <T> setPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    override suspend fun removePreferences(keys: List<Preferences.Key<*>>) {
        dataStore.edit { preferences ->
            keys.forEach { key ->
                preferences.remove(key)
            }
        }
    }

    override suspend fun <T> addToSet(
        key: Preferences.Key<Set<T>>,
        value: T
    ) {
        dataStore.edit { preferences ->
            val current = preferences[key] ?: emptySet()
            preferences[key] = current + value
        }
    }

    override fun <T> getSet(
        key: Preferences.Key<Set<T>>
    ): Flow<List<T>> =
        dataStore.data.map { preferences ->
            preferences[key]?.toList() ?: emptyList()
        }

    override suspend fun <T> removeFromSet(key: Preferences.Key<Set<T>>, value: T) {
        dataStore.edit { preferences ->
            val current = preferences[key] ?: emptySet()
            preferences[key] = current - value
        }
    }
}