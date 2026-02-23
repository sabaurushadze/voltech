package com.tbc.auth.data.repository.register

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.tbc.auth.domain.repository.register.RegisterRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var repository: RegisterRepository

    @Before
    fun setup() {
        firebaseAuth = mockk()
        repository = RegisterRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Test
    fun `register with valid credentials returns Success`() = runTest {

        // Given
        val authResult = mockk<com.google.firebase.auth.AuthResult>(relaxed = true)
        every {
            firebaseAuth.createUserWithEmailAndPassword("test@email.com", "Password1!")
        } returns Tasks.forResult(authResult)
        // When
        val result = repository.register("test@email.com", "Password1!")
        // Then
        assertTrue(result is Resource.Success)
        assertEquals(Unit, (result as Resource.Success).data)
    }

    @Test
    fun `register with existing email returns Failure with AccountAlreadyExists`() = runTest {

        // Given
        val exception = mockk<FirebaseAuthUserCollisionException>(relaxed = true)
        every {
            firebaseAuth.createUserWithEmailAndPassword("existing@email.com", "Password1!")
        } returns Tasks.forException(exception)
        // When
        val result = repository.register("existing@email.com", "Password1!")
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(
            DataError.Auth.AccountAlreadyExists,
            (result as Resource.Failure).error
        )
    }

    @Test
    fun `register with weak password returns Failure with WeakPassword`() = runTest {

        // Given
        val exception = mockk<FirebaseAuthWeakPasswordException>(relaxed = true)
        every {
            firebaseAuth.createUserWithEmailAndPassword("test@email.com", "123")
        } returns Tasks.forException(exception)
        // When
        val result = repository.register("test@email.com", "123")
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(
            DataError.Auth.WeakPassword,
            (result as Resource.Failure).error
        )
    }

    @Test
    fun `register with unknown exception returns Failure with Unknown`() = runTest {

        // Given
        every {
            firebaseAuth.createUserWithEmailAndPassword("test@email.com", "Password1!")
        } returns Tasks.forException(RuntimeException("Network error"))
        // When
        val result = repository.register("test@email.com", "Password1!")
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(
            DataError.Auth.Unknown,
            (result as Resource.Failure).error
        )
    }
}
