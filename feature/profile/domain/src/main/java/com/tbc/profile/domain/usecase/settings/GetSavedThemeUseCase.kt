package com.tbc.profile.domain.usecase.settings

import com.tbc.core.domain.datastore.preference_keys.VoltechPreferenceKeys
import com.tbc.core.domain.datastore.usecase.GetPreferenceUseCase
import com.tbc.profile.domain.model.settings.VoltechThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSavedThemeUseCase @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase
) {
    suspend operator fun invoke(): Flow<VoltechThemeOption> {
        return getPreferenceUseCase(VoltechPreferenceKeys.THEME_MODE_KEY, VoltechThemeOption.SYSTEM.name)
            .map(VoltechThemeOption::valueOf)
    }
}