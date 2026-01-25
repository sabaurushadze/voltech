package com.tbc.core.domain.datastore.preference_keys

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

object VoltechPreferenceKeys {
    val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    val RECENT_SEARCHES = stringSetPreferencesKey("recent_searches")
}