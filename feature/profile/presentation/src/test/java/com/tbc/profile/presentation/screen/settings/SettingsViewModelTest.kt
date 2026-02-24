package com.tbc.profile.presentation.screen.settings

import app.cash.turbine.test
import com.tbc.profile.domain.model.settings.VoltechThemeOption
import com.tbc.profile.domain.usecase.settings.GetSavedThemeUseCase
import com.tbc.profile.domain.usecase.settings.SaveThemeUseCase
import com.tbc.profile.domain.usecase.settings.SignOutUseCase
import com.tbc.core_testing.test.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest : CoroutineTestRule() {

    private val getSavedThemeUseCase = mockk<GetSavedThemeUseCase>()
    private val saveThemeUseCase = mockk<SaveThemeUseCase>()
    private val signOutUseCase = mockk<SignOutUseCase>()

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        coEvery { getSavedThemeUseCase() } returns flowOf(VoltechThemeOption.SYSTEM)
        coJustRun { saveThemeUseCase(any()) }
        justRun { signOutUseCase() }
        viewModel = SettingsViewModel(
            getSavedThemeUseCase = getSavedThemeUseCase,
            saveThemeUseCase = saveThemeUseCase,
            signOutUseCase = signOutUseCase,
        )
    }

    @Test
    fun `initial state loads theme from getSavedThemeUseCase`() = runTest(testDispatcher) {
        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals(VoltechThemeOption.SYSTEM, viewModel.state.value.themeOption)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `observeTheme updates state when theme changes`() = runTest(testDispatcher) {
        // Given
        coEvery { getSavedThemeUseCase() } returns flowOf(
            VoltechThemeOption.SYSTEM,
            VoltechThemeOption.DARK,
        )
        viewModel = SettingsViewModel(
            getSavedThemeUseCase = getSavedThemeUseCase,
            saveThemeUseCase = saveThemeUseCase,
            signOutUseCase = signOutUseCase,
        )
        // When
        advanceUntilIdle()
        // Then
        assertEquals(VoltechThemeOption.DARK, viewModel.state.value.themeOption)
    }

    @Test
    fun `onEvent NavigateBackToProfile emits NavigateBackToProfile side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(SettingsEvent.NavigateBackToProfile)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(SettingsSideEffect.NavigateBackToProfile::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent ThemeChanged calls saveThemeUseCase`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(SettingsEvent.ThemeChanged(VoltechThemeOption.DARK))
        advanceUntilIdle()
        // Then
        coVerify { saveThemeUseCase(VoltechThemeOption.DARK) }
    }

    @Test
    fun `onEvent SignOut calls signOutUseCase`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(SettingsEvent.SignOut)
        runCurrent()
        // Then
        verify { signOutUseCase() }
    }
}
