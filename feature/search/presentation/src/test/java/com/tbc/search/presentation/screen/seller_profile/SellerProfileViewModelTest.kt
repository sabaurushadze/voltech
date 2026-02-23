package com.tbc.search.presentation.screen.seller_profile

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.usecase.feed.GetLimitedItemsByUidUseCase
import com.tbc.search.domain.usecase.item_details.CalculateSellerPositiveFeedbackUseCase
import com.tbc.search.domain.usecase.item_details.CalculateTotalFeedbackReceivedUseCase
import com.tbc.search.domain.usecase.review.GetLimitedReviewUseCase
import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab
import com.tbc.search.presentation.screen.seler_profile.SellerProfileEvent
import com.tbc.search.presentation.screen.seler_profile.SellerProfileViewModel
import com.tbc.search.presentation.test.TestFixtures
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SellerProfileViewModelTest : CoroutineTestRule() {

    private val getLimitedReviewUseCase = mockk<GetLimitedReviewUseCase>()
    private val getSellersUseCase = mockk<GetSellersUseCase>()
    private val getLimitedItemsByUidUseCase = mockk<GetLimitedItemsByUidUseCase>()
    private val calculateSellerPositiveFeedback = CalculateSellerPositiveFeedbackUseCase()
    private val calculateTotalFeedbackReceived = CalculateTotalFeedbackReceivedUseCase()

    private lateinit var viewModel: SellerProfileViewModel

    @Before
    fun setup() {
        viewModel = SellerProfileViewModel(
            getLimitedReviewUseCase = getLimitedReviewUseCase,
            getSellersUseCase = getSellersUseCase,
            getLimitedItemsByUidUseCase = getLimitedItemsByUidUseCase,
            calculateSellerPositiveFeedback = calculateSellerPositiveFeedback,
            calculateTotalFeedbackReceived = calculateTotalFeedbackReceived,
        )
    }

    @Test
    fun `initial state is correct`() {

        // Given
        // When
        // Then
        assertNull(viewModel.state.value.seller)
        assertEquals("", viewModel.state.value.sellerUid)
        assertTrue(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertEquals(SellerProfileTab.STORE, viewModel.state.value.selectedTab)
        assertTrue(viewModel.state.value.sellerProductItem.isEmpty())
        assertTrue(viewModel.state.value.sellerReviewItems.isEmpty())
    }

    @Test
    fun `onEvent UpdateSellerUid updates sellerUid`() {

        // Given
        // When
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller123"))
        // Then
        assertEquals("seller123", viewModel.state.value.sellerUid)
    }

    @Test
    fun `onEvent SelectTab updates selectedTab`() {

        // Given
        // When
        viewModel.onEvent(SellerProfileEvent.SelectTab(SellerProfileTab.REVIEWS))
        // Then
        assertEquals(SellerProfileTab.REVIEWS, viewModel.state.value.selectedTab)
    }

    @Test
    fun `onEvent GetSeller success loads seller with calculated feedback`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        // When
        viewModel.onEvent(SellerProfileEvent.GetSeller)
        advanceUntilIdle()
        // Then
        val seller = viewModel.state.value.seller
        assertTrue(seller != null)
        assertEquals("Seller", seller?.sellerName)
        assertEquals(88.9, seller?.positiveFeedback)
        assertEquals(10, seller?.totalFeedback)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetSeller failure NO_CONNECTION sets showNoConnectionError true`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(SellerProfileEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertNull(viewModel.state.value.seller)
        assertTrue(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetSeller failure other sets showNoConnectionError false`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(SellerProfileEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertNull(viewModel.state.value.seller)
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetReviews success loads reviews`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedReviewUseCase(uid = "seller1", limit = 4) } returns Resource.Success(listOf(TestFixtures.review))
        // When
        viewModel.onEvent(SellerProfileEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertEquals(1, viewModel.state.value.sellerReviewItems.size)
        assertEquals("Title1", viewModel.state.value.sellerReviewItems[0].title)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetReviews failure NO_CONNECTION sets showNoConnectionError true`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedReviewUseCase(uid = "seller1", limit = 4) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(SellerProfileEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.sellerReviewItems.isEmpty())
        assertTrue(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetReviews failure other sets showNoConnectionError false`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedReviewUseCase(uid = "seller1", limit = 4) } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(SellerProfileEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.sellerReviewItems.isEmpty())
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetSellerLimitedItems success loads products`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedItemsByUidUseCase("seller1", 4) } returns Resource.Success(listOf(TestFixtures.feedItem))
        // When
        viewModel.onEvent(SellerProfileEvent.GetSellerLimitedItems)
        advanceUntilIdle()
        // Then
        assertEquals(1, viewModel.state.value.sellerProductItem.size)
        assertEquals("Item", viewModel.state.value.sellerProductItem[0].title)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetSellerLimitedItems failure NO_CONNECTION sets showNoConnectionError true`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedItemsByUidUseCase("seller1", 4) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(SellerProfileEvent.GetSellerLimitedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.sellerProductItem.isEmpty())
        assertTrue(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetSellerLimitedItems failure other sets showNoConnectionError false`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedItemsByUidUseCase("seller1", 4) } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(SellerProfileEvent.GetSellerLimitedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.sellerProductItem.isEmpty())
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent GetSeller success with empty list does not update seller`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(SellerProfileEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertNull(viewModel.state.value.seller)
    }

    @Test
    fun `GetSeller calls getSellersUseCase with sellerUid`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        // When
        viewModel.onEvent(SellerProfileEvent.GetSeller)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { getSellersUseCase("seller1") }
    }

    @Test
    fun `GetReviews calls getLimitedReviewUseCase with correct params`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedReviewUseCase(uid = "seller1", limit = 4) } returns Resource.Success(listOf(TestFixtures.review))
        // When
        viewModel.onEvent(SellerProfileEvent.GetReviews)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { getLimitedReviewUseCase(uid = "seller1", limit = 4) }
    }

    @Test
    fun `GetSellerLimitedItems calls getLimitedItemsByUidUseCase with correct params`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(SellerProfileEvent.UpdateSellerUid("seller1"))
        coEvery { getLimitedItemsByUidUseCase("seller1", 4) } returns Resource.Success(listOf(TestFixtures.feedItem))
        // When
        viewModel.onEvent(SellerProfileEvent.GetSellerLimitedItems)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { getLimitedItemsByUidUseCase("seller1", 4) }
    }
}
