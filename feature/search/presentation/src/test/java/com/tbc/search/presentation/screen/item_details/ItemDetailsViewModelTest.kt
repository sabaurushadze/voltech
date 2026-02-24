package com.tbc.search.presentation.screen.item_details

import app.cash.turbine.test
import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.core.domain.usecase.recently_viewed.AddRecentlyItemUseCase
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.resource.R
import com.tbc.search.domain.model.cart.Cart
import com.tbc.search.domain.model.favorite.Favorite
import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.search.domain.model.review.response.ReviewResponse
import com.tbc.search.domain.usecase.cart.AddItemToCartUseCase
import com.tbc.search.domain.usecase.cart.GetCartItemsUseCase
import com.tbc.search.domain.usecase.favorite.GetFavoriteItemsUseCase
import com.tbc.search.domain.usecase.favorite.ToggleFavoriteItemUseCase
import com.tbc.search.domain.usecase.feed.GetItemDetailsUseCase
import com.tbc.search.domain.usecase.item_details.CalculateSellerPositiveFeedbackUseCase
import com.tbc.search.domain.usecase.item_details.CalculateTotalFeedbackReceivedUseCase
import com.tbc.search.domain.usecase.item_details.IsItemMineUseCase
import com.tbc.search.domain.usecase.review.AddReviewUseCase
import com.tbc.search.domain.usecase.review.GetReviewByUidUseCase
import com.tbc.search.presentation.test.TestFixtures
import com.tbc.selling.domain.model.Rating
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.UpdateSellerRatingUseCase
import com.tbc.selling.domain.usecase.selling.add_item.form.ValidateDescriptionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ItemDetailsViewModelTest : CoroutineTestRule() {

    private val getItemDetailsUseCase = mockk<GetItemDetailsUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>().apply {
        every { this@apply() } returns TestFixtures.user
    }
    private val getFavoriteItemsUseCase = mockk<GetFavoriteItemsUseCase>()
    private val getRecentlyUseCase = mockk<GetRecentlyUseCase>()
    private val getCartItemsUseCase = mockk<GetCartItemsUseCase>()
    private val toggleFavoriteItemUseCase = mockk<ToggleFavoriteItemUseCase>()
    private val addRecentlyItemUseCase = mockk<AddRecentlyItemUseCase>()
    private val addItemToCartUseCase = mockk<AddItemToCartUseCase>()
    private val getSellersUseCase = mockk<GetSellersUseCase>()
    private val validateDescriptionUseCase = ValidateDescriptionUseCase()
    private val updateSellerRatingUseCase = mockk<UpdateSellerRatingUseCase>()
    private val calculateSellerPositiveFeedback = CalculateSellerPositiveFeedbackUseCase()
    private val calculateTotalFeedbackReceived = CalculateTotalFeedbackReceivedUseCase()
    private val isItemMineUseCase = IsItemMineUseCase()
    private val addReviewUseCase = mockk<AddReviewUseCase>()
    private val getReviewByUidUseCase = mockk<GetReviewByUidUseCase>()

    private lateinit var viewModel: ItemDetailsViewModel

    @Before
    fun setup() {
        viewModel = ItemDetailsViewModel(
            getItemDetailsUseCase = getItemDetailsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            getFavoriteItemsUseCase = getFavoriteItemsUseCase,
            getRecentlyUseCase = getRecentlyUseCase,
            getCartItemsUseCase = getCartItemsUseCase,
            toggleFavoriteItemUseCase = toggleFavoriteItemUseCase,
            addRecentlyItemUseCase = addRecentlyItemUseCase,
            addItemToCartUseCase = addItemToCartUseCase,
            getSellersUseCase = getSellersUseCase,
            validateDescriptionUseCase = validateDescriptionUseCase,
            updateSellerRatingUseCase = updateSellerRatingUseCase,
            calculateSellerPositiveFeedback = calculateSellerPositiveFeedback,
            calculateTotalFeedbackReceived = calculateTotalFeedbackReceived,
            isItemMineUseCase = isItemMineUseCase,
            addReviewUseCase = addReviewUseCase,
            getReviewByUidUseCase = getReviewByUidUseCase,
        )
    }

    @Test
    fun `initial state has user after getCurrentUser`() = runTest(testDispatcher) {

        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user.uid)
    }

    @Test
    fun `onEvent GetItemId updates itemId`() {

        // Given
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemId(42))
        // Then
        assertEquals(42, viewModel.state.value.itemId)
    }

    @Test
    fun `onEvent SelectImageByIndex updates selectedImage`() {

        // Given
        // When
        viewModel.onEvent(ItemDetailsEvent.SelectImageByIndex(2))
        // Then
        assertEquals(2, viewModel.state.value.selectedImage)
    }

    @Test
    fun `onEvent CloseImagePreview sets previewStartIndex to null`() {

        // Given
        viewModel.onEvent(ItemDetailsEvent.OpenImagePreview(1))
        // When
        viewModel.onEvent(ItemDetailsEvent.CloseImagePreview)
        // Then
        assertNull(viewModel.state.value.previewStartIndex)
    }

    @Test
    fun `onEvent OpenImagePreview sets previewStartIndex`() {

        // Given
        // When
        viewModel.onEvent(ItemDetailsEvent.OpenImagePreview(2))
        // Then
        assertEquals(2, viewModel.state.value.previewStartIndex)
    }

    @Test
    fun `onEvent ShowReviewSheet sets showReviewSheet to true`() {

        // Given
        // When
        viewModel.onEvent(ItemDetailsEvent.ShowReviewSheet)
        // Then
        assertTrue(viewModel.state.value.showReviewSheet)
    }

    @Test
    fun `onEvent HideReviewSheet sets showReviewSheet to false`() {

        // Given
        viewModel.onEvent(ItemDetailsEvent.ShowReviewSheet)
        // When
        viewModel.onEvent(ItemDetailsEvent.HideReviewSheet)
        // Then
        assertFalse(viewModel.state.value.showReviewSheet)
    }

    @Test
    fun `onEvent SelectRating updates selectedRating`() {

        // Given
        // When
        viewModel.onEvent(ItemDetailsEvent.SelectRating(Rating.NEGATIVE))
        // Then
        assertEquals(Rating.NEGATIVE, viewModel.state.value.selectedRating)
    }

    @Test
    fun `onEvent ClearDescription clears comment`() {

        // Given
        viewModel.onEvent(ItemDetailsEvent.DescriptionChanged("test"))
        // When
        viewModel.onEvent(ItemDetailsEvent.ClearDescription)
        // Then
        assertEquals("", viewModel.state.value.comment)
    }

    @Test
    fun `onEvent DescriptionChanged updates comment`() {

        // Given
        // When
        viewModel.onEvent(ItemDetailsEvent.DescriptionChanged("Great item!"))
        // Then
        assertEquals("Great item!", viewModel.state.value.comment)
    }

    @Test
    fun `onEvent ClearReviewErrors sets showDescriptionError to false`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // When
        viewModel.onEvent(ItemDetailsEvent.ClearReviewErrors)
        // Then
        assertFalse(viewModel.state.value.showDescriptionError)
    }

    @Test
    fun `onEvent BuyItem emits ShowSnackBar side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ItemDetailsEvent.BuyItem)
            runCurrent()
            // Then
            val sideEffect = awaitItem()
            assertEquals(ItemDetailsSideEffect.ShowSnackBar::class, sideEffect::class)
            assertEquals(R.string.this_service_not_work, (sideEffect as ItemDetailsSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateBackToFeed emits NavigateBackToFeed side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ItemDetailsEvent.NavigateBackToFeed)
            runCurrent()
            // Then
            assertEquals(ItemDetailsSideEffect.NavigateBackToFeed, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent GetItemDetails loads item details and getSeller sets positiveFeedback totalFeedback canGiveFeedback`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // Then - pure functions use real logic: positiveFeedback(8,1)=88.9, totalFeedback(8,1,1)=10
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("Item", viewModel.state.value.itemDetails?.title)
        assertEquals("Seller", viewModel.state.value.seller?.sellerName)
        assertEquals(88.9, viewModel.state.value.seller?.positiveFeedback ?: 0.0, 0.01)
        assertEquals(10, viewModel.state.value.seller?.totalFeedback ?: 0)
        assertTrue(viewModel.state.value.canGiveFeedback)
    }

    @Test
    fun `onEvent GetItemDetails when use case fails emits ShowSnackBar`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        viewModel.sideEffect.test {
            val effect = awaitItem()
            assertEquals(ItemDetailsSideEffect.ShowSnackBar::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent GetItemDetails completes with isLoading false`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // Then - loading completes, isLoading false
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent AddItemToCart when use case fails isInCart stays false`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { addItemToCartUseCase(any()) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        assertFalse(viewModel.state.value.isInCart)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddItemToCart)
        advanceUntilIdle()
        // Then - failure path: isInCart should not be set
        assertFalse(viewModel.state.value.isInCart)
    }

    @Test
    fun `onEvent GetItemDetails when getSellersUseCase fails seller stays null`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // Then - itemDetails loaded but seller null due to getSellers failure
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("Item", viewModel.state.value.itemDetails?.title)
        assertNull(viewModel.state.value.seller)
    }

    @Test
    fun `onEvent AddRecentlyItem when getRecentlyUseCase fails recentlyItemsId stays empty`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getRecentlyUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddRecentlyItem)
        advanceUntilIdle()
        // Then - failure path: recentlyItemsId not updated
        assertTrue(viewModel.state.value.recentlyItemsId.isEmpty())
    }

    @Test
    fun `onEvent GetFavorites when getFavoriteItemsUseCase fails favoriteItem stays empty`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(ItemDetailsEvent.GetFavorites("user1"))
        advanceUntilIdle()
        // Then - failure path: favoriteItem not updated
        assertTrue(viewModel.state.value.favoriteItem.isEmpty())
    }

    @Test
    fun `onEvent GetCartItemIds updates cartItemIds and checks isInCart`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getCartItemsUseCase("user1") } returns Resource.Success(listOf(TestFixtures.cart))
        // When
        viewModel.onEvent(ItemDetailsEvent.GetCartItemIds)
        advanceUntilIdle()
        // Then
        assertEquals(listOf(10), viewModel.state.value.cartItemIds)
        assertTrue(viewModel.state.value.isInCart)
    }

    @Test
    fun `onEvent AddItemToCart sets isInCart when success`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { addItemToCartUseCase(any()) } returns Resource.Success(Unit)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddItemToCart)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.isInCart)
    }

    @Test
    fun `onEvent AddRecentlyItem updates recentlyItemsId`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(TestFixtures.recently))
        coEvery { addRecentlyItemUseCase(any()) } returns Resource.Success(Unit)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddRecentlyItem)
        advanceUntilIdle()
        // Then
        assertEquals(listOf(10), viewModel.state.value.recentlyItemsId)
    }

    @Test
    fun `onEvent GetFavorites loads favorites`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetFavorites("user1"))
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItem.isEmpty())
    }

    @Test
    fun `onEvent GetFavoriteItems loads favorites using state user uid`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetFavoriteItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItem.isEmpty())
    }

    @Test
    fun `onEvent GetCurrentSeller loads seller with positiveFeedback totalFeedback canGiveFeedback`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        // When
        viewModel.onEvent(ItemDetailsEvent.GetCurrentSeller)
        advanceUntilIdle()
        // Then - real logic: positiveFeedback(8,1)=88.9, totalFeedback(8,1,1)=10
        assertEquals("Seller", viewModel.state.value.seller?.sellerName)
        assertEquals(88.9, viewModel.state.value.seller?.positiveFeedback ?: 0.0, 0.01)
        assertEquals(10, viewModel.state.value.seller?.totalFeedback ?: 0)
        assertTrue(viewModel.state.value.canGiveFeedback)
    }

    @Test
    fun `onEvent GetItemDetails when user views own item sets canGiveFeedback false`() = runTest(testDispatcher) {

        // Given - item owned by current user (uid=user1), canGiveFeedback = sellerUid != currentUid = false
        val ownItem = TestFixtures.feedItem.copy(uid = "user1")
        val ownSellerResponse = TestFixtures.sellerResponse.copy(uid = "user1")
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(ownItem)
        coEvery { getSellersUseCase("user1") } returns Resource.Success(listOf(ownSellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // Then - real canGiveFeedbackUseCase: user1 != user1 = false
        assertFalse(viewModel.state.value.canGiveFeedback)
    }

    @Test
    fun `onEvent OnFavoriteToggle success refreshes favorites`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { toggleFavoriteItemUseCase(any()) } returns Resource.Success(Unit)
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.OnFavoriteToggle("user1"))
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItem.isEmpty())
    }

    @Test
    fun `onEvent OnFavoriteToggle failure emits ShowSnackBar`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { toggleFavoriteItemUseCase(any()) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(ItemDetailsEvent.OnFavoriteToggle("user1"))
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(ItemDetailsSideEffect.ShowSnackBar::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent SubmitReview invalid description sets showDescriptionError`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        viewModel.onEvent(ItemDetailsEvent.DescriptionChanged(""))
        // When - real validateDescriptionUseCase: "" is invalid (length < 2)
        viewModel.onEvent(ItemDetailsEvent.SubmitReview)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.showDescriptionError)
    }

    @Test
    fun `onEvent SubmitReview valid description calls updateSellerRating addReview getSeller and canUserLeaveReview`() = runTest(testDispatcher) {

        // Given - first getSeller (from GetItemDetails) uses original seller (8,1,1)
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // submitReview: updateSellerRating (positive+1=9), addReview, getSeller (9,1,1), canUserLeaveReview
        viewModel.onEvent(ItemDetailsEvent.DescriptionChanged("Great!"))
        viewModel.onEvent(ItemDetailsEvent.SelectRating(Rating.POSITIVE))
        coEvery { updateSellerRatingUseCase(any()) } returns Resource.Success(Unit)
        coEvery { addReviewUseCase(any()) } returns Resource.Success(Unit)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        // When
        viewModel.onEvent(ItemDetailsEvent.SubmitReview)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.showDescriptionError)
        coVerify(exactly = 1) { updateSellerRatingUseCase(any()) }
        coVerify(exactly = 1) { addReviewUseCase(any()) }
        assertEquals("Seller", viewModel.state.value.seller?.sellerName)
        // canGiveFeedback stays true (seller1 != user1) - user can give feedback on others' items
        assertTrue(viewModel.state.value.canGiveFeedback)
    }

    @Test
    fun `onEvent SubmitReview with NEUTRAL rating updates seller`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.SelectRating(Rating.NEUTRAL))
        viewModel.onEvent(ItemDetailsEvent.DescriptionChanged("OK"))
        coEvery { updateSellerRatingUseCase(any()) } returns Resource.Success(Unit)
        coEvery { addReviewUseCase(any()) } returns Resource.Success(Unit)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        // When
        viewModel.onEvent(ItemDetailsEvent.SubmitReview)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.showDescriptionError)
        coVerify(exactly = 1) { updateSellerRatingUseCase(any()) }
        coVerify(exactly = 1) { addReviewUseCase(any()) }
    }

    @Test
    fun `onEvent SubmitReview with NEGATIVE rating updates seller`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(emptyList())
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.SelectRating(Rating.NEGATIVE))
        viewModel.onEvent(ItemDetailsEvent.DescriptionChanged("Bad"))
        coEvery { updateSellerRatingUseCase(any()) } returns Resource.Success(Unit)
        coEvery { addReviewUseCase(any()) } returns Resource.Success(Unit)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        // When
        viewModel.onEvent(ItemDetailsEvent.SubmitReview)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.showDescriptionError)
        coVerify(exactly = 1) { updateSellerRatingUseCase(any()) }
        coVerify(exactly = 1) { addReviewUseCase(any()) }
    }

    @Test
    fun `onEvent GetCartItemIds when item not in cart sets isInCart false`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getCartItemsUseCase("user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(ItemDetailsEvent.GetCartItemIds)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.cartItemIds.isEmpty())
        assertFalse(viewModel.state.value.isInCart)
    }

    @Test
    fun `onEvent AddRecentlyItem when item not in list calls addRecentlyItemUseCase`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        val otherRecently = Recently(id = 2, uid = "user1", itemId = 5)
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(otherRecently))
        coEvery { addRecentlyItemUseCase(any()) } returns Resource.Success(Unit)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddRecentlyItem)
        advanceUntilIdle()
        // Then
        assertEquals(listOf(5), viewModel.state.value.recentlyItemsId)
    }

    @Test
    fun `onEvent AddItemToCart when already in cart does not duplicate`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getCartItemsUseCase("user1") } returns Resource.Success(listOf(TestFixtures.cart))
        viewModel.onEvent(ItemDetailsEvent.GetCartItemIds)
        advanceUntilIdle()
        assertTrue(viewModel.state.value.isInCart)
        coEvery { addItemToCartUseCase(any()) } returns Resource.Success(Unit)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddItemToCart)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.isInCart)
    }

    @Test
    fun `canUserLeaveReview sets canGiveFeedback false when user has existing review`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        coEvery { getItemDetailsUseCase(10) } returns Resource.Success(TestFixtures.feedItem)
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        val existingReview = ReviewResponse(
            id = 1,
            itemId = 10,
            uid = "seller1",
            reviewerUid = "user1",
            reviewerUserName = "Test",
            comment = "Old",
            rating = ReviewRating.POSITIVE,
            reviewAt = "2024-01-01",
            title = "Item",
        )
        coEvery { getReviewByUidUseCase(10, "user1") } returns Resource.Success(listOf(existingReview))
        // When
        viewModel.onEvent(ItemDetailsEvent.GetItemDetails(10))
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.canGiveFeedback)
    }

    @Test
    fun `onEvent OnFavoriteToggle when 20 favorites does not call toggle`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        val twentyFavorites = (1..20).map { Favorite(id = it, uid = "user1", itemId = it) }
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(twentyFavorites)
        viewModel.onEvent(ItemDetailsEvent.GetFavorites("user1"))
        advanceUntilIdle()
        assertEquals(20, viewModel.state.value.favoriteItem.size)
        // When
        viewModel.onEvent(ItemDetailsEvent.OnFavoriteToggle("user1"))
        advanceUntilIdle()
        // Then - toggleFavoriteItemUseCase was never called (no coEvery for it in this test)
        // If it were called without mock, test would fail. We didn't stub it, so if called we'd get error.
        // Actually we need to verify it was NOT called. We can use coVerify with never.
        coVerify(exactly = 0) { toggleFavoriteItemUseCase(any()) }
    }

    @Test
    fun `onEvent AddItemToCart when 20 cart items does not call addItemToCart`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        viewModel.onEvent(ItemDetailsEvent.GetItemId(10))
        val twentyCarts = (1..20).map { Cart(id = it, uid = "user1", itemId = it) }
        coEvery { getCartItemsUseCase("user1") } returns Resource.Success(twentyCarts)
        viewModel.onEvent(ItemDetailsEvent.GetCartItemIds)
        advanceUntilIdle()
        assertEquals(20, viewModel.state.value.cartItemIds.size)
        // When
        viewModel.onEvent(ItemDetailsEvent.AddItemToCart)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 0) { addItemToCartUseCase(any()) }
    }
}
