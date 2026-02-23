package com.tbc.auth.presentation.screen.login

import app.cash.turbine.test
import com.tbc.auth.domain.usecase.ValidateEmailUseCase
import com.tbc.auth.domain.usecase.ValidatePasswordUseCase
import com.tbc.auth.domain.usecase.login.LogInWithEmailAndPasswordUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.resource.R
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogInViewModelTest : CoroutineTestRule() {

    private val logInUseCase = mockk<LogInWithEmailAndPasswordUseCase>()
    private val validateEmailUseCase = mockk<ValidateEmailUseCase>()
    private val validatePasswordUseCase = mockk<ValidatePasswordUseCase>()
    private val viewModel = LogInViewModel(
        logInUseCase = logInUseCase,
        validateEmailUseCase = validateEmailUseCase,
        validatePasswordUseCase = validatePasswordUseCase,
    )

    private fun setValidCredentials() {
        viewModel.onEvent(LogInEvent.EmailChanged("valid@email.com"))
        viewModel.onEvent(LogInEvent.PasswordChanged("password123"))
    }

    private fun mockValidCredentialsValidation() {
        every { validateEmailUseCase("valid@email.com") } returns true
        every { validatePasswordUseCase("password123") } returns true
    }

    @Test
    fun `initial state is correct`() {

        // Given
        // When
        // Then
        assertEquals("", viewModel.state.value.email)
        assertEquals("", viewModel.state.value.password)
        assertEquals(false, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.isPasswordVisible)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent EmailChanged updates email in state`() {

        // Given
        // When
        viewModel.onEvent(LogInEvent.EmailChanged("test@email.com"))
        // Then
        assertEquals("test@email.com", viewModel.state.value.email)
    }

    @Test
    fun `onEvent PasswordChanged updates password in state`() {

        // Given
        // When
        viewModel.onEvent(LogInEvent.PasswordChanged("password123"))
        // Then
        assertEquals("password123", viewModel.state.value.password)
    }

    @Test
    fun `onEvent PasswordVisibilityChanged toggles password visibility`() {

        // Given
        assertEquals(false, viewModel.state.value.isPasswordVisible)
        // When
        viewModel.onEvent(LogInEvent.PasswordVisibilityChanged)
        // Then
        assertEquals(true, viewModel.state.value.isPasswordVisible)
        // When
        viewModel.onEvent(LogInEvent.PasswordVisibilityChanged)
        // Then
        assertEquals(false, viewModel.state.value.isPasswordVisible)
    }

    @Test
    fun `onEvent NavigateToRegister emits NavigateToRegister side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(LogInEvent.NavigateToRegister)
            runCurrent()
            // Then
            assertEquals(LogInSideEffect.NavigateToRegister, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent LogIn with valid credentials emits Success side effect`() = runTest(testDispatcher) {

        // Given
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            logInUseCase(email = "valid@email.com", password = "password123")
        } returns Resource.Success(Unit)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(LogInEvent.LogIn)
            advanceUntilIdle()
            // Then
            assertEquals(LogInSideEffect.Success, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent LogIn with invalid credentials emits ShowSnackBar side effect`() = runTest(testDispatcher) {
        // Given
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            logInUseCase(email = "valid@email.com", password = "password123")
        } returns Resource.Failure(DataError.Auth.InvalidCredential)
        viewModel.sideEffect.test {

            // When
            viewModel.onEvent(LogInEvent.LogIn)
            advanceUntilIdle()

            // Then
            val sideEffect = awaitItem()
            assertEquals(LogInSideEffect.ShowSnackBar::class, sideEffect::class)
            assertEquals(R.string.email_or_password_is_incorrect, (sideEffect as LogInSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent LogIn with invalid email shows email error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(LogInEvent.EmailChanged("invalid-email"))
        viewModel.onEvent(LogInEvent.PasswordChanged("password123"))
        every { validateEmailUseCase("invalid-email") } returns false
        every { validatePasswordUseCase("password123") } returns true
        // When
        viewModel.onEvent(LogInEvent.LogIn)
        advanceUntilIdle()
        // Then
        assertEquals(true, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent LogIn with invalid password shows password error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(LogInEvent.EmailChanged("valid@email.com"))
        viewModel.onEvent(LogInEvent.PasswordChanged(""))
        every { validateEmailUseCase("valid@email.com") } returns true
        every { validatePasswordUseCase("") } returns false
        // When
        viewModel.onEvent(LogInEvent.LogIn)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(true, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent LogIn with both invalid email and password shows both errors`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(LogInEvent.EmailChanged("invalid"))
        viewModel.onEvent(LogInEvent.PasswordChanged(""))
        every { validateEmailUseCase("invalid") } returns false
        every { validatePasswordUseCase("") } returns false
        // When
        viewModel.onEvent(LogInEvent.LogIn)
        advanceUntilIdle()
        // Then
        assertEquals(true, viewModel.state.value.showEmailError)
        assertEquals(true, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent LogIn sets loading state during login`() = runTest(testDispatcher) {

        // Given
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            logInUseCase(email = "valid@email.com", password = "password123")
        } coAnswers {
            kotlinx.coroutines.delay(100)
            Resource.Success(Unit)
        }
        // When
        viewModel.onEvent(LogInEvent.LogIn)
        runCurrent()
        // Then
        assertEquals(true, viewModel.state.value.isLoading)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent LogIn clears previous errors when retrying`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(LogInEvent.EmailChanged("invalid"))
        viewModel.onEvent(LogInEvent.PasswordChanged(""))
        every { validateEmailUseCase("invalid") } returns false
        every { validatePasswordUseCase("") } returns false
        viewModel.onEvent(LogInEvent.LogIn)
        advanceUntilIdle()
        assertEquals(true, viewModel.state.value.showEmailError)
        // When
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            logInUseCase(email = "valid@email.com", password = "password123")
        } returns Resource.Success(Unit)
        viewModel.onEvent(LogInEvent.LogIn)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.showPasswordError)
    }
}
