package com.tbc.core.domain.datastore.manager

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> setPreference(key: Preferences.Key<T>, value: T)
    suspend fun removePreferences(keys: List<Preferences.Key<*>>)

    suspend fun <T> addToSet(key: Preferences.Key<Set<T>>, value: T)

    fun <T> getSet(key: Preferences.Key<Set<T>>): Flow<List<T>>

    suspend fun <T> removeFromSet(key: Preferences.Key<Set<T>>, value: T)
}