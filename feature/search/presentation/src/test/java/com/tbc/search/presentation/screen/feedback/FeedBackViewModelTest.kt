package com.tbc.search.presentation.screen.feedback

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.search.domain.usecase.item_details.CalculateSellerPositiveFeedbackUseCase
import com.tbc.search.domain.usecase.item_details.CalculateTotalFeedbackReceivedUseCase
import com.tbc.search.domain.usecase.review.GetReviewUseCase
import com.tbc.search.presentation.enums.feedback.FeedbackFilterType
import com.tbc.search.presentation.enums.feedback.FeedbackSortType
import com.tbc.search.presentation.test.TestFixtures
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedBackViewModelTest : CoroutineTestRule() {

    private val getReviewUseCase = mockk<GetReviewUseCase>()
    private val getSellersUseCase = mockk<GetSellersUseCase>()
    private val calculateSellerPositiveFeedback = mockk<CalculateSellerPositiveFeedbackUseCase>()
    private val calculateTotalFeedbackReceived = mockk<CalculateTotalFeedbackReceivedUseCase>()

    private lateinit var viewModel: FeedBackViewModel

    @Before
    fun setup() {
        viewModel = FeedBackViewModel(
            getReviewUseCase = getReviewUseCase,
            getSellersUseCase = getSellersUseCase,
            calculateSellerPositiveFeedback = calculateSellerPositiveFeedback,
            calculateTotalFeedbackReceived = calculateTotalFeedbackReceived,
        )
    }

    @Test
    fun `initial state is correct`() {

        // Given
        // When
        // Then
        assertEquals(null, viewModel.state.value.seller)
        assertTrue(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertEquals("", viewModel.state.value.sellerUid)
        assertFalse(viewModel.state.value.isSortShow)
        assertFalse(viewModel.state.value.isFilterShow)
        assertTrue(viewModel.state.value.sellerReviewItems.isEmpty())
        assertTrue(viewModel.state.value.modifiedSellerReviewItems.isEmpty())
        assertEquals(FeedbackSortType.NEWEST, viewModel.state.value.selectedSortType)
        assertEquals(FeedbackFilterType.ALL_FEEDBACK, viewModel.state.value.selectedFilterType)
    }

    @Test
    fun `onEvent UpdateSellerUid updates sellerUid`() {

        // Given
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller123"))
        // Then
        assertEquals("seller123", viewModel.state.value.sellerUid)
    }

    @Test
    fun `onEvent UpdateSelectedSortType updates selectedSortType`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(emptyList())
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedSortType(FeedbackSortType.OLDEST))
        // Then
        assertEquals(FeedbackSortType.OLDEST, viewModel.state.value.selectedSortType)
    }

    @Test
    fun `onEvent UpdateSelectedFilterType updates selectedFilterType`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.POSITIVE_FEEDBACK))
        // Then
        assertEquals(FeedbackFilterType.POSITIVE_FEEDBACK, viewModel.state.value.selectedFilterType)
    }

    @Test
    fun `onEvent UpdateSortVisibilityStatus toggles isSortShow`() {

        // Given
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSortVisibilityStatus)
        // Then
        assertTrue(viewModel.state.value.isSortShow)
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSortVisibilityStatus)
        // Then
        assertFalse(viewModel.state.value.isSortShow)
    }

    @Test
    fun `onEvent UpdateFilterVisibilityStatus toggles isFilterShow`() {

        // Given
        // When
        viewModel.onEvent(FeedBackEvent.UpdateFilterVisibilityStatus)
        // Then
        assertTrue(viewModel.state.value.isFilterShow)
        // When
        viewModel.onEvent(FeedBackEvent.UpdateFilterVisibilityStatus)
        // Then
        assertFalse(viewModel.state.value.isFilterShow)
    }

    @Test
    fun `onEvent GetReviews loads reviews successfully`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        // When
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(2, viewModel.state.value.sellerReviewItems.size)
        assertEquals(2, viewModel.state.value.modifiedSellerReviewItems.size)
    }

    @Test
    fun `onEvent GetReviews when getReviewUseCase fails with NO_CONNECTION shows error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetSeller loads seller successfully`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponse))
        every { calculateSellerPositiveFeedback(8, 1) } returns 80.0
        every { calculateTotalFeedbackReceived(8, 1, 1) } returns 10
        // When
        viewModel.onEvent(FeedBackEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("Seller", viewModel.state.value.seller?.sellerName)
        assertEquals(80.0, viewModel.state.value.seller?.positiveFeedback ?: 0.0, 0.01)
        assertEquals(10, viewModel.state.value.seller?.totalFeedback)
    }

    @Test
    fun `onEvent GetSeller when getSellersUseCase fails with NO_CONNECTION shows error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(FeedBackEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent UpdateSelectedSortType NEWEST sorts reviews descending by date`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedSortType(FeedbackSortType.NEWEST))
        // Then
        assertEquals(FeedbackSortType.NEWEST, viewModel.state.value.selectedSortType)
        assertEquals("2024-01-02T10:00:00Z", viewModel.state.value.modifiedSellerReviewItems.first().reviewAt)
    }

    @Test
    fun `onEvent UpdateSelectedSortType OLDEST sorts reviews ascending by date`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedSortType(FeedbackSortType.OLDEST))
        // Then
        assertEquals(FeedbackSortType.OLDEST, viewModel.state.value.selectedSortType)
        assertEquals("2024-01-01T10:00:00Z", viewModel.state.value.modifiedSellerReviewItems.first().reviewAt)
    }

    @Test
    fun `onEvent UpdateSelectedFilterType POSITIVE_FEEDBACK filters reviews`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.POSITIVE_FEEDBACK))
        // Then
        assertEquals(1, viewModel.state.value.modifiedSellerReviewItems.size)
        assertEquals(ReviewRating.POSITIVE, viewModel.state.value.modifiedSellerReviewItems.first().rating)
    }

    @Test
    fun `onEvent UpdateSelectedFilterType ALL_FEEDBACK shows all reviews`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.POSITIVE_FEEDBACK))
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.ALL_FEEDBACK))
        // Then
        assertEquals(2, viewModel.state.value.modifiedSellerReviewItems.size)
    }

    @Test
    fun `onEvent UpdateSelectedFilterType NEUTRAL_FEEDBACK filters reviews`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2, TestFixtures.review3))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.NEUTRAL_FEEDBACK))
        // Then
        assertEquals(1, viewModel.state.value.modifiedSellerReviewItems.size)
        assertEquals(ReviewRating.NEUTRAL, viewModel.state.value.modifiedSellerReviewItems.first().rating)
    }

    @Test
    fun `onEvent UpdateSelectedFilterType NEGATIVE_FEEDBACK filters reviews`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // When
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.NEGATIVE_FEEDBACK))
        // Then
        assertEquals(1, viewModel.state.value.modifiedSellerReviewItems.size)
        assertEquals(ReviewRating.NEGATIVE, viewModel.state.value.modifiedSellerReviewItems.first().rating)
    }

    @Test
    fun `onEvent GetReviews when getReviewUseCase fails with non NO_CONNECTION error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetSeller when getSellersUseCase fails with non NO_CONNECTION error`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(FeedBackEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetSeller when sellers list is empty does not update seller`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(FeedBackEvent.GetSeller)
        advanceUntilIdle()
        // Then
        assertEquals(null, viewModel.state.value.seller)
    }

    @Test
    fun `onEvent GetReviews when modifiedSellerReviewItems already has data does not overwrite it`() = runTest(testDispatcher) {

        // Given
        viewModel.onEvent(FeedBackEvent.UpdateSellerUid("seller1"))
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2))
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        viewModel.onEvent(FeedBackEvent.UpdateSelectedFilterType(FeedbackFilterType.POSITIVE_FEEDBACK))
        val filteredCount = viewModel.state.value.modifiedSellerReviewItems.size
        coEvery { getReviewUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.review, TestFixtures.review2, TestFixtures.review3))
        // When
        viewModel.onEvent(FeedBackEvent.GetReviews)
        advanceUntilIdle()
        // Then
        assertEquals(3, viewModel.state.value.sellerReviewItems.size)
        assertEquals(filteredCount, viewModel.state.value.modifiedSellerReviewItems.size)
    }
}
