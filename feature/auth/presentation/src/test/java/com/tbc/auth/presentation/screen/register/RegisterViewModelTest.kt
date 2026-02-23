package com.tbc.auth.presentation.screen.register

import app.cash.turbine.test
import com.tbc.auth.domain.usecase.ValidateEmailUseCase
import com.tbc.auth.domain.usecase.register.RegisterWithEmailAndPasswordUseCase
import com.tbc.auth.domain.usecase.register.ValidateRegistrationPasswordUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.profile.domain.usecase.edit_profile.UpdateUserNameUseCase
import com.tbc.profile.domain.usecase.edit_profile.ValidateUserNameUseCase
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
class RegisterViewModelTest : CoroutineTestRule() {

    private val registerUseCase = mockk<RegisterWithEmailAndPasswordUseCase>()
    private val validateEmailUseCase = mockk<ValidateEmailUseCase>()
    private val validateRegistrationPasswordUseCase = mockk<ValidateRegistrationPasswordUseCase>()
    private val updateUserNameUseCase = mockk<UpdateUserNameUseCase>()
    private val validateUserNameUseCase = mockk<ValidateUserNameUseCase>()
    private val viewModel = RegisterViewModel(
        registerUseCase = registerUseCase,
        validateEmailUseCase = validateEmailUseCase,
        validateRegistrationPasswordUseCase = validateRegistrationPasswordUseCase,
        updateUserNameUseCase = updateUserNameUseCase,
        validateUserNameUseCase = validateUserNameUseCase,
    )

    private fun setValidCredentials() {
        viewModel.onEvent(RegisterEvent.EmailChanged("valid@email.com"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("Password1!"))
        viewModel.onEvent(RegisterEvent.UsernameChanged("johndoe"))
    }

    private fun mockValidCredentialsValidation() {
        every { validateEmailUseCase("valid@email.com") } returns true
        every { validateRegistrationPasswordUseCase("Password1!") } returns true
        every { validateUserNameUseCase("johndoe") } returns true
    }

    @Test
    fun `initial state is correct`() {

        // Given
        // When
        // Then
        assertEquals("", viewModel.state.value.email)
        assertEquals("", viewModel.state.value.password)
        assertEquals("", viewModel.state.value.username)
        assertEquals(false, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.showUsernameError)
        assertEquals(false, viewModel.state.value.isPasswordVisible)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent EmailChanged updates email in state`() {

        // Given
        // When
        viewModel.onEvent(RegisterEvent.EmailChanged("test@email.com"))
        // Then
        assertEquals("test@email.com", viewModel.state.value.email)
    }

    @Test
    fun `onEvent PasswordChanged updates password in state`() {

        // Given
        // When
        viewModel.onEvent(RegisterEvent.PasswordChanged("password123"))
        // Then
        assertEquals("password123", viewModel.state.value.password)
    }

    @Test
    fun `onEvent UsernameChanged updates username in state`() {

        // Given
        // When
        viewModel.onEvent(RegisterEvent.UsernameChanged("john_doe"))
        // Then
        assertEquals("john_doe", viewModel.state.value.username)
    }

    @Test
    fun `onEvent PasswordVisibilityChanged toggles password visibility`() {

        // Given
        assertEquals(false, viewModel.state.value.isPasswordVisible)
        // When
        viewModel.onEvent(RegisterEvent.PasswordVisibilityChanged)
        // Then
        assertEquals(true, viewModel.state.value.isPasswordVisible)
        // When
        viewModel.onEvent(RegisterEvent.PasswordVisibilityChanged)
        // Then
        assertEquals(false, viewModel.state.value.isPasswordVisible)
    }

    @Test
    fun `onEvent Register with valid credentials emits Success side effect`() = runTest(testDispatcher) {

        // Given
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            registerUseCase(email = "valid@email.com", password = "Password1!")
        } returns Resource.Success(Unit)
        coEvery { updateUserNameUseCase("johndoe") } returns Resource.Success(Unit)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(RegisterEvent.Register)
            advanceUntilIdle()
            // Then
            assertEquals(RegisterSideEffect.Success, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register with invalid credentials emits ShowSnackBar side effect`() = runTest(testDispatcher) {

        // Given
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            registerUseCase(email = "valid@email.com", password = "Password1!")
        } returns Resource.Failure(DataError.Auth.AccountAlreadyExists)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(RegisterEvent.Register)
            advanceUntilIdle()
            // Then
            val sideEffect = awaitItem()
            assertEquals(RegisterSideEffect.ShowSnackBar::class, sideEffect::class)
            assertEquals(R.string.error_permission_denied, (sideEffect as RegisterSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register with invalid email shows email error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(RegisterEvent.EmailChanged("invalid-email"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("Password1!"))
        viewModel.onEvent(RegisterEvent.UsernameChanged("johndoe"))
        every { validateEmailUseCase("invalid-email") } returns false
        every { validateRegistrationPasswordUseCase("Password1!") } returns true
        every { validateUserNameUseCase("johndoe") } returns true
        // When
        viewModel.onEvent(RegisterEvent.Register)
        advanceUntilIdle()
        // Then
        assertEquals(true, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.showUsernameError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register with invalid password shows password error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(RegisterEvent.EmailChanged("valid@email.com"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("weak"))
        viewModel.onEvent(RegisterEvent.UsernameChanged("johndoe"))
        every { validateEmailUseCase("valid@email.com") } returns true
        every { validateRegistrationPasswordUseCase("weak") } returns false
        every { validateUserNameUseCase("johndoe") } returns true
        // When
        viewModel.onEvent(RegisterEvent.Register)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(true, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.showUsernameError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register with invalid username shows username error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(RegisterEvent.EmailChanged("valid@email.com"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("Password1!"))
        viewModel.onEvent(RegisterEvent.UsernameChanged("x"))
        every { validateEmailUseCase("valid@email.com") } returns true
        every { validateRegistrationPasswordUseCase("Password1!") } returns true
        every { validateUserNameUseCase("x") } returns false
        // When
        viewModel.onEvent(RegisterEvent.Register)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.showPasswordError)
        assertEquals(true, viewModel.state.value.showUsernameError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register with multiple invalid fields shows all errors`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(RegisterEvent.EmailChanged("invalid"))
        viewModel.onEvent(RegisterEvent.PasswordChanged(""))
        viewModel.onEvent(RegisterEvent.UsernameChanged(""))
        every { validateEmailUseCase("invalid") } returns false
        every { validateRegistrationPasswordUseCase("") } returns false
        every { validateUserNameUseCase("") } returns false
        // When
        viewModel.onEvent(RegisterEvent.Register)
        advanceUntilIdle()
        // Then
        assertEquals(true, viewModel.state.value.showEmailError)
        assertEquals(true, viewModel.state.value.showPasswordError)
        assertEquals(true, viewModel.state.value.showUsernameError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register sets loading state during registration`() = runTest(testDispatcher) {

        // Given
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            registerUseCase(email = "valid@email.com", password = "Password1!")
        } coAnswers {
            kotlinx.coroutines.delay(100)
            Resource.Success(Unit)
        }
        coEvery { updateUserNameUseCase("johndoe") } returns Resource.Success(Unit)
        // When
        viewModel.onEvent(RegisterEvent.Register)
        runCurrent()
        // Then
        assertEquals(true, viewModel.state.value.isLoading)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent Register clears previous errors when retrying`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(RegisterEvent.EmailChanged("invalid"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("weak"))
        viewModel.onEvent(RegisterEvent.UsernameChanged("x"))
        every { validateEmailUseCase("invalid") } returns false
        every { validateRegistrationPasswordUseCase("weak") } returns false
        every { validateUserNameUseCase("x") } returns false
        viewModel.onEvent(RegisterEvent.Register)
        advanceUntilIdle()
        assertEquals(true, viewModel.state.value.showEmailError)
        // When
        setValidCredentials()
        mockValidCredentialsValidation()
        coEvery {
            registerUseCase(email = "valid@email.com", password = "Password1!")
        } returns Resource.Success(Unit)
        coEvery { updateUserNameUseCase("johndoe") } returns Resource.Success(Unit)
        viewModel.onEvent(RegisterEvent.Register)
        advanceUntilIdle()
        // Then
        assertEquals(false, viewModel.state.value.showEmailError)
        assertEquals(false, viewModel.state.value.showPasswordError)
        assertEquals(false, viewModel.state.value.showUsernameError)
    }
}
