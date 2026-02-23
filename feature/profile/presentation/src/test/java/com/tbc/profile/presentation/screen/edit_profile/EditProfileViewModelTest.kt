package com.tbc.profile.presentation.screen.edit_profile

import app.cash.turbine.test
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.profile.domain.usecase.edit_profile.DeleteFileUseCase
import com.tbc.profile.domain.usecase.edit_profile.EnqueueFileUploadUseCase
import com.tbc.profile.domain.usecase.edit_profile.UpdateProfilePictureUseCase
import com.tbc.profile.domain.usecase.edit_profile.UpdateUserNameUseCase
import com.tbc.profile.domain.usecase.edit_profile.ValidateUserNameUseCase
import com.tbc.resource.R
import com.tbc.selling.domain.model.SellerResponse
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.UpdateSellerProfileUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EditProfileViewModelTest : CoroutineTestRule() {

    private val testUser = User(uid = "user1", name = "John", photoUrl = "photo.png")
    private val testSellerResponse = SellerResponse(
        id = 1,
        uid = "user1",
        positive = 0,
        neutral = 0,
        negative = 0,
        sellerPhotoUrl = "photo.png",
        sellerName = "John",
    )

    private val enqueueFileUploadUseCase = mockk<EnqueueFileUploadUseCase>()
    private val deleteFileUseCase = mockk<DeleteFileUseCase>()
    private val updateProfilePictureUseCase = mockk<UpdateProfilePictureUseCase>()
    private val updateUserNameUseCase = mockk<UpdateUserNameUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val validateUserNameUseCase = mockk<ValidateUserNameUseCase>()
    private val getSellersUseCase = mockk<GetSellersUseCase>()
    private val updateSellerProfileUseCase = mockk<UpdateSellerProfileUseCase>()

    private lateinit var viewModel: EditProfileViewModel

    @Before
    fun setup() {
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getSellersUseCase("user1") } returns Resource.Success(listOf(testSellerResponse))
        viewModel = EditProfileViewModel(
            enqueueFileUploadUseCase = enqueueFileUploadUseCase,
            deleteFileUseCase = deleteFileUseCase,
            updateProfilePictureUseCase = updateProfilePictureUseCase,
            updateUserNameUseCase = updateUserNameUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            validateUserNameUseCase = validateUserNameUseCase,
            getSellersUseCase = getSellersUseCase,
            updateSellerProfileUseCase = updateSellerProfileUseCase,
        )
    }

    @Test
    fun `initial state loads user and seller`() = runTest(testDispatcher) {

        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user?.uid)
        assertEquals("John", viewModel.state.value.user?.name)
        assertEquals("John", viewModel.state.value.userName)
        assertEquals(1, viewModel.state.value.seller?.id)
        assertEquals("John", viewModel.state.value.seller?.sellerName)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.selectedProfileEdit)
    }

    @Test
    fun `onEvent NavigateBackToProfile emits NavigateToProfile side effect`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(EditProfileEvent.NavigateBackToProfile)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(EditProfileSideEffect.NavigateToProfile::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent ShowProfileEditSheet sets selectedProfileEdit true`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(EditProfileEvent.ShowProfileEditSheet)
        // Then
        assertTrue(viewModel.state.value.selectedProfileEdit)
    }

    @Test
    fun `onEvent HideProfileEditSheet sets selectedProfileEdit false`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(EditProfileEvent.ShowProfileEditSheet)
        // When
        viewModel.onEvent(EditProfileEvent.HideProfileEditSheet)
        // Then
        assertFalse(viewModel.state.value.selectedProfileEdit)
    }

    @Test
    fun `onEvent OnLaunchGallery emits LaunchGallery side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(EditProfileEvent.OnLaunchGallery)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(EditProfileSideEffect.LaunchGallery::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent UserNameChanged updates userName in state`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(EditProfileEvent.UserNameChanged("NewName"))
        // Then
        assertEquals("NewName", viewModel.state.value.userName)
    }

    @Test
    fun `onEvent SaveUsername with invalid username sets showUsernameError`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        every { validateUserNameUseCase("x") } returns false
        viewModel.onEvent(EditProfileEvent.UserNameChanged("x"))
        // When
        viewModel.onEvent(EditProfileEvent.SaveUsername("x"))
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.showUsernameError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent SaveUsername with valid username success shows snackbar`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        every { validateUserNameUseCase("JohnDoe") } returns true
        viewModel.onEvent(EditProfileEvent.UserNameChanged("JohnDoe"))
        coEvery { updateUserNameUseCase("JohnDoe") } returns Resource.Success(Unit)
        coEvery { updateSellerProfileUseCase(any()) } returns Resource.Success(Unit)
        every { getCurrentUserUseCase() } returns testUser.copy(name = "JohnDoe")
        coEvery { getSellersUseCase("user1") } returns Resource.Success(
            listOf(testSellerResponse.copy(sellerName = "JohnDoe"))
        )
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(EditProfileEvent.SaveUsername("JohnDoe"))
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(EditProfileSideEffect.ShowSnackBar::class, effect::class)
            assertEquals(R.string.username_updated, (effect as EditProfileSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent SaveUsername when updateUserNameUseCase fails shows error snackbar`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        every { validateUserNameUseCase("JohnDoe") } returns true
        viewModel.onEvent(EditProfileEvent.UserNameChanged("JohnDoe"))
        coEvery { updateUserNameUseCase("JohnDoe") } returns Resource.Failure(DataError.Firestore.Unknown)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(EditProfileEvent.SaveUsername("JohnDoe"))
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(R.string.username_update_failed, (effect as EditProfileSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent DeletePhotoFromStorage with null url does nothing`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(EditProfileEvent.DeletePhotoFromStorage(null))
        advanceUntilIdle()
        // Then
        assertEquals("photo.png", viewModel.state.value.seller?.sellerPhotoUrl)
    }

    @Test
    fun `onEvent DeletePhotoFromStorage success deletes and shows snackbar`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { deleteFileUseCase("photo.png") } returns Resource.Success(Unit)
        coEvery { updateProfilePictureUseCase(null) } returns Resource.Success(Unit)
        coEvery { updateSellerProfileUseCase(any()) } returns Resource.Success(Unit)
        every { getCurrentUserUseCase() } returns testUser.copy(photoUrl = null)
        coEvery { getSellersUseCase("user1") } returns Resource.Success(
            listOf(testSellerResponse.copy(sellerPhotoUrl = null))
        )
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(EditProfileEvent.DeletePhotoFromStorage("photo.png"))
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(R.string.profile_picture_deleted, (effect as EditProfileSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent DeletePhotoFromStorage failure shows delete failed snackbar`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { deleteFileUseCase("photo.png") } returns Resource.Failure(DataError.Firestore.Unknown)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(EditProfileEvent.DeletePhotoFromStorage("photo.png"))
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(R.string.delete_failed, (effect as EditProfileSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `saveButtonEnabled is true when userName differs from user name`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(EditProfileEvent.UserNameChanged("DifferentName"))
        // When
        // Then
        assertTrue(viewModel.state.value.saveButtonEnabled)
    }

    @Test
    fun `saveButtonEnabled is false when no changes`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        // When - userName equals user.name, no selectedImageUri
        // Then
        assertFalse(viewModel.state.value.saveButtonEnabled)
    }
}
