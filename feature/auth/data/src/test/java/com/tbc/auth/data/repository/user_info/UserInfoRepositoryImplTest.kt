package com.tbc.auth.data.repository.user_info

import app.cash.turbine.test
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tbc.auth.domain.repository.user_info.UserInfoRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserInfoRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var repository: UserInfoRepository

    @Before
    fun setup() {
        firebaseAuth = mockk()
        repository = UserInfoRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Test
    fun `isUserAuthenticated returns true when currentUser is not null`() {

        // Given
        every { firebaseAuth.currentUser } returns mockk<FirebaseUser>()
        // When
        val result = repository.isUserAuthenticated()
        // Then
        assertTrue(result)
    }

    @Test
    fun `isUserAuthenticated returns false when currentUser is null`() {

        // Given
        every { firebaseAuth.currentUser } returns null
        // When
        val result = repository.isUserAuthenticated()
        // Then
        assertFalse(result)
    }

    @Test
    fun `getAuthState emits true when user is authenticated`() = runTest {

        // Given
        val authWithUser = mockk<FirebaseAuth> {
            every { currentUser } returns mockk<FirebaseUser>()
        }
        every { firebaseAuth.addAuthStateListener(any()) } answers {
            firstArg<FirebaseAuth.AuthStateListener>().onAuthStateChanged(authWithUser)
            mockk()
        }
        every { firebaseAuth.removeAuthStateListener(any()) } returns Unit
        // When
        repository.getAuthState().test {
            // Then
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAuthState emits false when user is not authenticated`() = runTest {

        // Given
        val authWithoutUser = mockk<FirebaseAuth> {
            every { currentUser } returns null
        }
        every { firebaseAuth.addAuthStateListener(any()) } answers {
            firstArg<FirebaseAuth.AuthStateListener>().onAuthStateChanged(authWithoutUser)
            mockk()
        }
        every { firebaseAuth.removeAuthStateListener(any()) } returns Unit
        // When
        repository.getAuthState().test {
            // Then
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
