package com.tbc.profile.presentation.screen.profile

import app.cash.turbine.test
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core_testing.test.CoroutineTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest : CoroutineTestRule() {

    private val testUser = User(uid = "user1", name = "John", photoUrl = "photo.png")

    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        every { getCurrentUserUseCase() } returns testUser
        viewModel = ProfileViewModel(getCurrentUserUseCase = getCurrentUserUseCase)
    }

    @Test
    fun `initial state has null user`() = runTest(testDispatcher) {
        // Given / When
        // Then
        assertNull(viewModel.state.value.user)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetUserInfo loads user and updates state`() = runTest(testDispatcher) {
        // Given
        // When
        viewModel.onEvent(ProfileEvent.GetUserInfo)
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user?.uid)
        assertEquals("John", viewModel.state.value.user?.name)
        assertEquals("photo.png", viewModel.state.value.user?.photoUrl)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetUserInfo when user is null updates state with null`() = runTest(testDispatcher) {
        // Given
        every { getCurrentUserUseCase() } returns null
        viewModel = ProfileViewModel(getCurrentUserUseCase = getCurrentUserUseCase)
        // When
        viewModel.onEvent(ProfileEvent.GetUserInfo)
        advanceUntilIdle()
        // Then
        assertNull(viewModel.state.value.user)
    }

    @Test
    fun `onEvent NavigateToSettings emits NavigateToSettings side effect`() = runTest(testDispatcher) {
        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ProfileEvent.NavigateToSettings)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(ProfileSideEffect.NavigateToSettings::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateToUserDetails emits NavigateToUserDetails side effect`() = runTest(testDispatcher) {
        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ProfileEvent.NavigateToUserDetails)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(ProfileSideEffect.NavigateToUserDetails::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateToWatchlist emits NavigateToWatchlist side effect`() = runTest(testDispatcher) {
        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ProfileEvent.NavigateToWatchlist)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(ProfileSideEffect.NavigateToWatchlist::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateToAddToCart emits NavigateToAddToCart side effect`() = runTest(testDispatcher) {
        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ProfileEvent.NavigateToAddToCart)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(ProfileSideEffect.NavigateToAddToCart::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
