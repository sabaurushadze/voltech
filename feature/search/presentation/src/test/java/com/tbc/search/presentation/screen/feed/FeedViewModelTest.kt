package com.tbc.search.presentation.screen.feed

import app.cash.turbine.test
import androidx.paging.PagingData
import com.tbc.core.domain.model.category.Category
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.usecase.feed.GetFeedItemsPagingUseCase
import com.tbc.search.presentation.test.TestFixtures
import com.tbc.search.presentation.enums.feed.SortType
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest : CoroutineTestRule() {

    private val getFeedItemsPagingUseCase = mockk<GetFeedItemsPagingUseCase>().apply {
        every { this@apply(any(), any()) } returns flowOf(PagingData.from(listOf(TestFixtures.feedItemMinimal)))
    }
    private val viewModel = FeedViewModel(
        getFeedItemsPagingUseCase = getFeedItemsPagingUseCase,
    )

    @Test
    fun `initial state is correct`() {

        // Given
        // When
        // Then
        assertEquals("price", viewModel.state.value.query.sortBy)
        assertFalse(viewModel.state.value.selectedSort)
        assertFalse(viewModel.state.value.selectedFilter)
        assertEquals(SortType.PRICE_LOWEST, viewModel.state.value.selectedSortType)
        assertFalse(viewModel.state.value.initialCategoryConsumed)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent SaveSearchQuery updates query with titleLike`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.SaveSearchQuery("laptop"))
        // Then
        assertEquals("laptop", viewModel.state.value.query.titleLike)
    }

    @Test
    fun `onEvent SaveSearchQuery resets sortBy and sortDescending`() {

        // Given
        viewModel.onEvent(FeedEvent.SelectSortType(SortType.PRICE_HIGHEST))
        // When
        viewModel.onEvent(FeedEvent.SaveSearchQuery("search"))
        // Then
        assertEquals("price", viewModel.state.value.query.sortBy)
        assertFalse(viewModel.state.value.query.sortDescending)
    }

    @Test
    fun `onEvent SaveCategoryQuery updates query with category`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.SaveCategoryQuery("GPU"))
        // Then
        assertEquals(listOf("GPU"), viewModel.state.value.query.category)
        assertEquals(setOf(Category.GPU), viewModel.state.value.filterState.selectedCategories)
        assertTrue(viewModel.state.value.initialCategoryConsumed)
    }

    @Test
    fun `onEvent SaveCategoryQuery with invalid category does not update state`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.SaveCategoryQuery("INVALID"))
        // Then
        assertEquals(null, viewModel.state.value.query.category)
        assertTrue(viewModel.state.value.filterState.selectedCategories.isEmpty())
    }

    @Test
    fun `onEvent SaveCategoryQuery when initialCategoryConsumed is true does not update state`() {

        // Given
        viewModel.onEvent(FeedEvent.SaveCategoryQuery("GPU"))
        assertEquals(listOf("GPU"), viewModel.state.value.query.category)
        // When
        viewModel.onEvent(FeedEvent.SaveCategoryQuery("CPU"))
        // Then
        assertEquals(listOf("GPU"), viewModel.state.value.query.category)
        assertEquals(setOf(Category.GPU), viewModel.state.value.filterState.selectedCategories)
    }

    @Test
    fun `onEvent ShowSortSheet sets selectedSort to true`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.ShowSortSheet)
        // Then
        assertTrue(viewModel.state.value.selectedSort)
    }

    @Test
    fun `onEvent HideSortSheet sets selectedSort to false`() {

        // Given
        viewModel.onEvent(FeedEvent.ShowSortSheet)
        // When
        viewModel.onEvent(FeedEvent.HideSortSheet)
        // Then
        assertFalse(viewModel.state.value.selectedSort)
    }

    @Test
    fun `onEvent SelectSortType PRICE_HIGHEST updates selectedSortType and sortDescending`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.SelectSortType(SortType.PRICE_HIGHEST))
        // Then
        assertEquals(SortType.PRICE_HIGHEST, viewModel.state.value.selectedSortType)
        assertTrue(viewModel.state.value.query.sortDescending)
        assertFalse(viewModel.state.value.selectedSort)
    }

    @Test
    fun `onEvent SelectSortType PRICE_LOWEST sets sortDescending to false`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.SelectSortType(SortType.PRICE_LOWEST))
        // Then
        assertEquals(SortType.PRICE_LOWEST, viewModel.state.value.selectedSortType)
        assertFalse(viewModel.state.value.query.sortDescending)
    }

    @Test
    fun `onEvent ShowFilterSheet sets selectedFilter to true`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.ShowFilterSheet)
        // Then
        assertTrue(viewModel.state.value.selectedFilter)
    }

    @Test
    fun `onEvent HideFilterSheet sets selectedFilter to false`() {

        // Given
        viewModel.onEvent(FeedEvent.ShowFilterSheet)
        // When
        viewModel.onEvent(FeedEvent.HideFilterSheet)
        // Then
        assertFalse(viewModel.state.value.selectedFilter)
    }

    @Test
    fun `onEvent UpdateMinPrice updates filterState minPrice`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.UpdateMinPrice("100"))
        // Then
        assertEquals("100", viewModel.state.value.filterState.minPrice)
    }

    @Test
    fun `onEvent UpdateMaxPrice updates filterState maxPrice`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.UpdateMaxPrice("500"))
        // Then
        assertEquals("500", viewModel.state.value.filterState.maxPrice)
    }

    @Test
    fun `onEvent ToggleCategory adds category when selected`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.ToggleCategory(Category.GPU, true))
        // Then
        assertEquals(setOf(Category.GPU), viewModel.state.value.filterState.selectedCategories)
    }

    @Test
    fun `onEvent ToggleCategory removes category when not selected`() {

        // Given
        viewModel.onEvent(FeedEvent.ToggleCategory(Category.GPU, true))
        // When
        viewModel.onEvent(FeedEvent.ToggleCategory(Category.GPU, false))
        // Then
        assertTrue(viewModel.state.value.filterState.selectedCategories.isEmpty())
    }

    @Test
    fun `onEvent ToggleCondition adds condition when selected`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.ToggleCondition(Condition.NEW, true))
        // Then
        assertEquals(setOf(Condition.NEW), viewModel.state.value.filterState.selectedConditions)
    }

    @Test
    fun `onEvent ToggleCondition removes condition when not selected`() {

        // Given
        viewModel.onEvent(FeedEvent.ToggleCondition(Condition.NEW, true))
        // When
        viewModel.onEvent(FeedEvent.ToggleCondition(Condition.NEW, false))
        // Then
        assertTrue(viewModel.state.value.filterState.selectedConditions.isEmpty())
    }

    @Test
    fun `onEvent ToggleLocation adds location when selected`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.ToggleLocation(Location.DIDI_DIGHOMI, true))
        // Then
        assertEquals(setOf(Location.DIDI_DIGHOMI), viewModel.state.value.filterState.selectedLocations)
    }

    @Test
    fun `onEvent ToggleLocation removes location when not selected`() {

        // Given
        viewModel.onEvent(FeedEvent.ToggleLocation(Location.DIDI_DIGHOMI, true))
        // When
        viewModel.onEvent(FeedEvent.ToggleLocation(Location.DIDI_DIGHOMI, false))
        // Then
        assertTrue(viewModel.state.value.filterState.selectedLocations.isEmpty())
    }

    @Test
    fun `onEvent FilterItems updates query with full filter state`() {

        // Given
        viewModel.onEvent(FeedEvent.ToggleCategory(Category.GPU, true))
        viewModel.onEvent(FeedEvent.UpdateMinPrice("50"))
        viewModel.onEvent(FeedEvent.UpdateMaxPrice("200"))
        // When
        viewModel.onEvent(FeedEvent.FilterItems("search"))
        // Then
        assertEquals("search", viewModel.state.value.query.titleLike)
        assertEquals(listOf("GPU"), viewModel.state.value.query.category)
        assertEquals(50f, viewModel.state.value.query.minPrice)
        assertEquals(200f, viewModel.state.value.query.maxPrice)
        assertFalse(viewModel.state.value.selectedFilter)
    }

    @Test
    fun `onEvent FilterItems with blank currentQuery sets titleLike to null`() {

        // Given
        viewModel.onEvent(FeedEvent.SaveSearchQuery("laptop"))
        // When
        viewModel.onEvent(FeedEvent.FilterItems(""))
        // Then
        assertEquals(null, viewModel.state.value.query.titleLike)
    }

    @Test
    fun `onEvent FilterItems with empty filters sets category condition location to null`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.FilterItems("query"))
        // Then
        assertEquals("query", viewModel.state.value.query.titleLike)
        assertEquals(null, viewModel.state.value.query.category)
        assertEquals(null, viewModel.state.value.query.condition)
        assertEquals(null, viewModel.state.value.query.location)
        assertEquals(null, viewModel.state.value.query.minPrice)
        assertEquals(null, viewModel.state.value.query.maxPrice)
    }

    @Test
    fun `onEvent FilterItems with invalid minPrice maxPrice sets them to null`() {

        // Given
        viewModel.onEvent(FeedEvent.UpdateMinPrice("invalid"))
        viewModel.onEvent(FeedEvent.UpdateMaxPrice("abc"))
        // When
        viewModel.onEvent(FeedEvent.FilterItems(""))
        // Then
        assertEquals(null, viewModel.state.value.query.minPrice)
        assertEquals(null, viewModel.state.value.query.maxPrice)
    }

    @Test
    fun `onEvent FilterItems with condition and location updates query`() {

        // Given
        viewModel.onEvent(FeedEvent.ToggleCondition(Condition.USED, true))
        viewModel.onEvent(FeedEvent.ToggleLocation(Location.GLDANI, true))
        // When
        viewModel.onEvent(FeedEvent.FilterItems("test"))
        // Then
        assertEquals(listOf("USED"), viewModel.state.value.query.condition)
        assertEquals(listOf("GLDANI"), viewModel.state.value.query.location)
    }

    @Test
    fun `onEvent FeedItemClick emits NavigateToItemDetails side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(FeedEvent.FeedItemClick(42))
            runCurrent()
            // Then
            val sideEffect = awaitItem()
            assertEquals(FeedSideEffect.NavigateToItemDetails::class, sideEffect::class)
            assertEquals(42, (sideEffect as FeedSideEffect.NavigateToItemDetails).id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent GetSellerItemsByUid updates query with uid`() {

        // Given
        // When
        viewModel.onEvent(FeedEvent.GetSellerItemsByUid("seller123"))
        // Then
        assertEquals("seller123", viewModel.state.value.query.uid)
        assertEquals(null, viewModel.state.value.query.titleLike)
        assertEquals(null, viewModel.state.value.query.category)
    }

    @Test
    fun `feedPagingFlow emits when query changes`() = runTest(testDispatcher) {

        // Given
        viewModel.feedPagingFlow.test {
            // When
            viewModel.onEvent(FeedEvent.SaveSearchQuery("laptop"))
            advanceUntilIdle()
            // Then
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
