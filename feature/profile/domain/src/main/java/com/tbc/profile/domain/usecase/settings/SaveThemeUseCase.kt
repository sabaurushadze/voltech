package com.tbc.profile.domain.usecase.settings

import com.tbc.core.domain.datastore.preference_keys.VoltechPreferenceKeys
import com.tbc.core.domain.datastore.usecase.SetPreferenceUseCase
import com.tbc.profile.domain.model.settings.VoltechThemeOption
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val setPreferencesUseCase: SetPreferenceUseCase
) {
    suspend operator fun invoke(theme: VoltechThemeOption) {
        setPreferencesUseCase(VoltechPreferenceKeys.THEME_MODE_KEY,theme.name)
    }
}