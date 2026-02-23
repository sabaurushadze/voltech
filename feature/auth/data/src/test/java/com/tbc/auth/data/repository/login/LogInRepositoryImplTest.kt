package com.tbc.auth.data.repository.login

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.tbc.auth.domain.repository.login.LogInRepository
import com.tbc.core.domain.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LogInRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var repository: LogInRepository

    @Before
    fun setup() {
        firebaseAuth = mockk()
        repository = LogInRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Test
    fun `logIn with valid credentials returns Success`() = runTest {

        // Given
        val authResult = mockk<com.google.firebase.auth.AuthResult>(relaxed = true)
        every {
            firebaseAuth.signInWithEmailAndPassword("test@email.com", "password123")
        } returns Tasks.forResult(authResult)
        // When
        val result = repository.logIn("test@email.com", "password123")
        // Then
        assertTrue(result is Resource.Success)
        assertEquals(Unit, (result as Resource.Success).data)
    }

    @Test
    fun `logIn with invalid credentials returns Failure with InvalidCredential`() = runTest {

        // Given
        val exception = mockk<FirebaseAuthInvalidCredentialsException>(relaxed = true)
        every {
            firebaseAuth.signInWithEmailAndPassword("test@email.com", "wrong")
        } returns Tasks.forException(exception)
        // When
        val result = repository.logIn("test@email.com", "wrong")
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(
            com.tbc.core.domain.util.DataError.Auth.InvalidCredential,
            (result as Resource.Failure).error
        )
    }

    @Test
    fun `logIn with unknown exception returns Failure with Unknown`() = runTest {

        // Given
        every {
            firebaseAuth.signInWithEmailAndPassword("test@email.com", "password")
        } returns Tasks.forException(RuntimeException("Network error"))
        // When
        val result = repository.logIn("test@email.com", "password")
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(
            com.tbc.core.domain.util.DataError.Auth.Unknown,
            (result as Resource.Failure).error
        )
    }
}
