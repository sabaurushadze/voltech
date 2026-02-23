package com.tbc.selling.presentation.screen.add_item

import android.net.Uri
import app.cash.turbine.test
import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.profile.domain.usecase.edit_profile.EnqueueMultipleFileUploadUseCase
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.usecase.feed.AddItemUseCase
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.AddSellerUseCase
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import com.tbc.selling.domain.usecase.selling.add_item.form.ValidateDescriptionUseCase
import com.tbc.selling.domain.usecase.selling.add_item.form.ValidatePriceUseCase
import com.tbc.selling.domain.usecase.selling.add_item.form.ValidateQuantityUseCase
import com.tbc.selling.domain.usecase.selling.add_item.form.ValidateTitleUseCase
import com.tbc.selling.domain.model.SellerResponse
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddItemViewModelTest : CoroutineTestRule() {

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

    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val enqueueMultipleFileUploadUseCase = mockk<EnqueueMultipleFileUploadUseCase>()
    private val validateTitleUseCase = mockk<ValidateTitleUseCase>()
    private val validateDescriptionUseCase = mockk<ValidateDescriptionUseCase>()
    private val validateQuantityUseCase = mockk<ValidateQuantityUseCase>()
    private val validatePriceUseCase = mockk<ValidatePriceUseCase>()
    private val addItemUseCase = mockk<AddItemUseCase>()
    private val addSellerUseCase = mockk<AddSellerUseCase>()
    private val getSellersUseCase = mockk<GetSellersUseCase>()

    private lateinit var viewModel: AddItemViewModel

    private val testUri = mockk<Uri>(relaxed = true)

    @Before
    fun setup() {
        every { testUri.toString() } returns "content://test/image"
        mockkStatic(Uri::class)
        every { Uri.parse(any<String>()) } returns testUri
        every { getCurrentUserUseCase() } returns testUser
        every { validateTitleUseCase("Valid Title") } returns true
        every { validateDescriptionUseCase("Valid description") } returns true
        every { validateQuantityUseCase("1") } returns true
        every { validatePriceUseCase("10.0") } returns true
        coEvery { getSellersUseCase("user1") } returns Resource.Success(listOf(testSellerResponse))
        coJustRun { addSellerUseCase(any()) }
        viewModel = AddItemViewModel(
            getCurrentUserUseCase = getCurrentUserUseCase,
            enqueueMultipleFileUploadUseCase = enqueueMultipleFileUploadUseCase,
            validateTitleUseCase = validateTitleUseCase,
            validateDescriptionUseCase = validateDescriptionUseCase,
            validateQuantityUseCase = validateQuantityUseCase,
            validatePriceUseCase = validatePriceUseCase,
            addItemUseCase = addItemUseCase,
            addSellerUseCase = addSellerUseCase,
            getSellersSeller = getSellersUseCase,
        )
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
    }

    @Test
    fun `initial state loads user`() = runTest(testDispatcher) {
        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user?.uid)
        assertEquals("John", viewModel.state.value.user?.name)
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.selectedImageUris.isEmpty())
    }

    @Test
    fun `onEvent NavigateBackToMyItems emits NavigateBackToMyItems side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(AddItemEvent.NavigateBackToMyItems)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(AddItemSideEffect.NavigateBackToMyItems::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent LaunchGallery emits LaunchGallery side effect`() = runTest(testDispatcher) {
        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(AddItemEvent.LaunchGallery)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(AddItemSideEffect.LaunchGallery::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent SelectCategory updates selectedCategory`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.SelectCategory(Category.GPU))
        // Then
        assertEquals(Category.GPU, viewModel.state.value.selectedCategory)
    }

    @Test
    fun `onEvent SelectCondition updates selectedCondition`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.SelectCondition(Condition.NEW))
        // Then
        assertEquals(Condition.NEW, viewModel.state.value.selectedCondition)
    }

    @Test
    fun `onEvent SelectLocation updates selectedLocation`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.SelectLocation(Location.DIDI_DIGHOMI))
        // Then
        assertEquals(Location.DIDI_DIGHOMI, viewModel.state.value.selectedLocation)
    }

    @Test
    fun `onEvent OnImagesSelected adds uris to selectedImageUris`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        val uri = Uri.parse("content://test/image")
        // When
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(uri)))
        // Then
        assertEquals(1, viewModel.state.value.selectedImageUris.size)
        assertEquals(uri, viewModel.state.value.selectedImageUris[0])
    }

    @Test
    fun `onEvent DeleteImageFromPreview removes image at index`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        val uri = Uri.parse("content://test/image")
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(uri)))
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(Uri.parse("content://test/image2"))))
        assertEquals(2, viewModel.state.value.selectedImageUris.size)
        // When
        viewModel.onEvent(AddItemEvent.DeleteImageFromPreview(0))
        // Then
        assertEquals(1, viewModel.state.value.selectedImageUris.size)
    }

    @Test
    fun `onEvent TitleChanged updates title`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.TitleChanged("My Item"))
        // Then
        assertEquals("My Item", viewModel.state.value.title)
    }

    @Test
    fun `onEvent DescriptionChanged updates description`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Item description"))
        // Then
        assertEquals("Item description", viewModel.state.value.description)
    }

    @Test
    fun `onEvent PriceChanged updates price`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.PriceChanged("99.99"))
        // Then
        assertEquals("99.99", viewModel.state.value.price)
    }

    @Test
    fun `onEvent QuantityChanged updates quantity`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.QuantityChanged("5"))
        // Then
        assertEquals("5", viewModel.state.value.quantity)
    }

    @Test
    fun `onEvent ClearDescription clears description`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Some text"))
        assertEquals("Some text", viewModel.state.value.description)
        // When
        viewModel.onEvent(AddItemEvent.ClearDescription)
        // Then
        assertEquals("", viewModel.state.value.description)
    }

    @Test
    fun `onEvent ResetImageError clears showImageError`() = runTest(testDispatcher) {
        // Given - AddItem with no images sets showImageError
        advanceUntilIdle()
        viewModel.onEvent(AddItemEvent.SelectCategory(Category.GPU))
        viewModel.onEvent(AddItemEvent.SelectCondition(Condition.NEW))
        viewModel.onEvent(AddItemEvent.SelectLocation(Location.DIDI_DIGHOMI))
        viewModel.onEvent(AddItemEvent.TitleChanged("Valid Title"))
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Valid description"))
        viewModel.onEvent(AddItemEvent.PriceChanged("10.0"))
        viewModel.onEvent(AddItemEvent.QuantityChanged("1"))
        viewModel.onEvent(AddItemEvent.AddItem)
        advanceUntilIdle()
        assertTrue(viewModel.state.value.showImageError)
        // When
        viewModel.onEvent(AddItemEvent.ResetImageError)
        // Then
        assertFalse(viewModel.state.value.showImageError)
    }

    @Test
    fun `onEvent OnPreviewImage sets previewStartIndex`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddItemEvent.OnPreviewImage(2))
        // Then
        assertEquals(2, viewModel.state.value.previewStartIndex)
    }

    @Test
    fun `onEvent DismissPreview clears previewStartIndex`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(AddItemEvent.OnPreviewImage(2))
        assertEquals(2, viewModel.state.value.previewStartIndex)
        // When
        viewModel.onEvent(AddItemEvent.DismissPreview)
        // Then
        assertNull(viewModel.state.value.previewStartIndex)
    }

    @Test
    fun `onEvent AddItem with invalid form shows errors`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        every { validateTitleUseCase("") } returns false
        every { validateDescriptionUseCase("") } returns false
        every { validateQuantityUseCase("") } returns false
        every { validatePriceUseCase("") } returns false
        viewModel.onEvent(AddItemEvent.TitleChanged(""))
        // When
        viewModel.onEvent(AddItemEvent.AddItem)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.showTitleError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent AddItem with valid form success navigates back`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(AddItemEvent.SelectCategory(Category.GPU))
        viewModel.onEvent(AddItemEvent.SelectCondition(Condition.NEW))
        viewModel.onEvent(AddItemEvent.SelectLocation(Location.DIDI_DIGHOMI))
        viewModel.onEvent(AddItemEvent.TitleChanged("Valid Title"))
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Valid description"))
        viewModel.onEvent(AddItemEvent.PriceChanged("10.0"))
        viewModel.onEvent(AddItemEvent.QuantityChanged("1"))
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(Uri.parse("content://test/image"))))
        coEvery { enqueueMultipleFileUploadUseCase(any()) } returns Resource.Success(listOf("url1"))
        coEvery { addItemUseCase(any()) } returns Resource.Success(Unit)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(AddItemEvent.AddItem)
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(AddItemSideEffect.NavigateBackToMyItems::class, effect::class)
            assertFalse(viewModel.state.value.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent AddItem when enqueueMultipleFileUpload fails clears loading`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(AddItemEvent.SelectCategory(Category.GPU))
        viewModel.onEvent(AddItemEvent.SelectCondition(Condition.NEW))
        viewModel.onEvent(AddItemEvent.SelectLocation(Location.DIDI_DIGHOMI))
        viewModel.onEvent(AddItemEvent.TitleChanged("Valid Title"))
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Valid description"))
        viewModel.onEvent(AddItemEvent.PriceChanged("10.0"))
        viewModel.onEvent(AddItemEvent.QuantityChanged("1"))
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(Uri.parse("content://test/image"))))
        coEvery { enqueueMultipleFileUploadUseCase(any()) } returns Resource.Failure(com.tbc.core.domain.util.DataError.Firestore.Unknown)
        // When
        viewModel.onEvent(AddItemEvent.AddItem)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent AddItem when addItemUseCase fails clears loading`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(AddItemEvent.SelectCategory(Category.GPU))
        viewModel.onEvent(AddItemEvent.SelectCondition(Condition.NEW))
        viewModel.onEvent(AddItemEvent.SelectLocation(Location.DIDI_DIGHOMI))
        viewModel.onEvent(AddItemEvent.TitleChanged("Valid Title"))
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Valid description"))
        viewModel.onEvent(AddItemEvent.PriceChanged("10.0"))
        viewModel.onEvent(AddItemEvent.QuantityChanged("1"))
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(Uri.parse("content://test/image"))))
        coEvery { enqueueMultipleFileUploadUseCase(any()) } returns Resource.Success(listOf("url1"))
        coEvery { addItemUseCase(any()) } returns Resource.Failure(com.tbc.core.domain.util.DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(AddItemEvent.AddItem)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent AddItem when user is not in sellers list calls addSellerUseCase`() = runTest(testDispatcher) {
        // Given - getSellersUseCase returns list without user1, so addSeller branch is executed
        advanceUntilIdle()
        coEvery { getSellersUseCase("user1") } returns Resource.Success(emptyList())
        viewModel.onEvent(AddItemEvent.SelectCategory(Category.GPU))
        viewModel.onEvent(AddItemEvent.SelectCondition(Condition.NEW))
        viewModel.onEvent(AddItemEvent.SelectLocation(Location.DIDI_DIGHOMI))
        viewModel.onEvent(AddItemEvent.TitleChanged("Valid Title"))
        viewModel.onEvent(AddItemEvent.DescriptionChanged("Valid description"))
        viewModel.onEvent(AddItemEvent.PriceChanged("10.0"))
        viewModel.onEvent(AddItemEvent.QuantityChanged("1"))
        viewModel.onEvent(AddItemEvent.OnImagesSelected(listOf(Uri.parse("content://test/image"))))
        coEvery { enqueueMultipleFileUploadUseCase(any()) } returns Resource.Success(listOf("url1"))
        coEvery { addItemUseCase(any()) } returns Resource.Success(Unit)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(AddItemEvent.AddItem)
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(AddItemSideEffect.NavigateBackToMyItems::class, effect::class)
            coVerify { addSellerUseCase(match { it.uid == "user1" && it.sellerName == "John" }) }
            cancelAndIgnoreRemainingEvents()
        }
    }
}
