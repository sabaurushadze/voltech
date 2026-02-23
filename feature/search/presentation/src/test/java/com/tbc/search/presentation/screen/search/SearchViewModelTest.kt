package com.tbc.search.presentation.screen.search

import app.cash.turbine.test
import com.tbc.core.domain.datastore.preference_keys.VoltechPreferenceKeys
import com.tbc.core.domain.datastore.usecase.AddToSetPreferenceUseCase
import com.tbc.core.domain.datastore.usecase.GetSetPreferenceUseCase
import com.tbc.core.domain.datastore.usecase.RemoveFromSetPreferenceUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.model.search.SearchItem
import com.tbc.search.domain.usecase.search.SearchItemByQueryUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.coJustRun
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest : CoroutineTestRule() {

    private val testSearchItems = listOf(
        SearchItem(title = "Laptop"),
        SearchItem(title = "Phone"),
    )

    private val searchItemByQueryUseCase = mockk<SearchItemByQueryUseCase>()
    private val addToSetPreferenceUseCase = mockk<AddToSetPreferenceUseCase>()
    private val getSetPreferenceUseCase = mockk<GetSetPreferenceUseCase>()
    private val removeFromSetPreferenceUseCase = mockk<RemoveFromSetPreferenceUseCase>()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        every { getSetPreferenceUseCase(VoltechPreferenceKeys.RECENT_SEARCHES) } returns flowOf(emptyList<String>())
        viewModel = SearchViewModel(
            searchItemByQueryUseCase = searchItemByQueryUseCase,
            addToSetPreferenceUseCase = addToSetPreferenceUseCase,
            getSetPreferenceUseCase = getSetPreferenceUseCase,
            removeFromSetPreferenceUseCase = removeFromSetPreferenceUseCase,
        )
    }

    @Test
    fun `initial state is correct`() = runTest(testDispatcher) {

        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("", viewModel.state.value.query)
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.titles.isEmpty())
        assertTrue(viewModel.state.value.recentSearchList.isEmpty())
    }

    @Test
    fun `onEvent QueryChanged updates query in state`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { searchItemByQueryUseCase("laptop") } returns Resource.Success(testSearchItems)
        // When
        viewModel.onEvent(SearchEvent.QueryChanged("laptop"))
        // Then
        assertEquals("laptop", viewModel.state.value.query)
    }

    @Test
    fun `onEvent SearchByQuery success loads titles`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { searchItemByQueryUseCase("laptop") } returns Resource.Success(testSearchItems)
        // When
        viewModel.onEvent(SearchEvent.SearchByQuery("laptop"))
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(2, viewModel.state.value.titles.size)
        assertEquals("Laptop", viewModel.state.value.titles[0].title)
        assertEquals("Phone", viewModel.state.value.titles[1].title)
    }

    @Test
    fun `onEvent SearchByQuery failure emits ShowSnackBar`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { searchItemByQueryUseCase("laptop") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(SearchEvent.SearchByQuery("laptop"))
            advanceUntilIdle()
            // Then
            val effect = awaitItem()
            assertEquals(SearchSideEffect.ShowSnackBar::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent SearchByQuery failure sets isLoading false`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { searchItemByQueryUseCase("laptop") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(SearchEvent.SearchByQuery("laptop"))
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent NavigateToFeedWithQuery emits NavigateToFeed side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(SearchEvent.NavigateToFeedWithQuery("laptop"))
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(SearchSideEffect.NavigateToFeed::class, effect::class)
            assertEquals("laptop", (effect as SearchSideEffect.NavigateToFeed).query)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent SaveRecentSearch calls addToSetPreferenceUseCase`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coJustRun { addToSetPreferenceUseCase(VoltechPreferenceKeys.RECENT_SEARCHES, any<String>()) }
        // When
        viewModel.onEvent(SearchEvent.SaveRecentSearch("laptop"))
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { addToSetPreferenceUseCase(VoltechPreferenceKeys.RECENT_SEARCHES, "laptop") }
    }

    @Test
    fun `onEvent RemoveRecentSearch calls removeFromSetPreferenceUseCase`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coJustRun { removeFromSetPreferenceUseCase(VoltechPreferenceKeys.RECENT_SEARCHES, any<String>()) }
        // When
        viewModel.onEvent(SearchEvent.RemoveRecentSearch("laptop"))
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { removeFromSetPreferenceUseCase(VoltechPreferenceKeys.RECENT_SEARCHES, "laptop") }
    }

    @Test
    fun `fetchRecentSearches loads recent search list from preferences`() = runTest(testDispatcher) {

        // Given
        val recentSearches = listOf("laptop", "phone")
        every { getSetPreferenceUseCase(VoltechPreferenceKeys.RECENT_SEARCHES) } returns flowOf(recentSearches)
        viewModel = SearchViewModel(
            searchItemByQueryUseCase = searchItemByQueryUseCase,
            addToSetPreferenceUseCase = addToSetPreferenceUseCase,
            getSetPreferenceUseCase = getSetPreferenceUseCase,
            removeFromSetPreferenceUseCase = removeFromSetPreferenceUseCase,
        )
        // When
        advanceUntilIdle()
        // Then
        assertEquals(recentSearches, viewModel.state.value.recentSearchList)
    }

    @Test
    fun `QueryChanged with blank after debounce clears titles`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { searchItemByQueryUseCase("lap") } returns Resource.Success(testSearchItems)
        viewModel.onEvent(SearchEvent.QueryChanged("lap"))
        advanceTimeBy(400)
        advanceUntilIdle()
        assertEquals(2, viewModel.state.value.titles.size)
        // When
        viewModel.onEvent(SearchEvent.QueryChanged(""))
        advanceTimeBy(400)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.titles.isEmpty())
    }

    @Test
    fun `SearchByQuery sets isLoading true then false on success`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { searchItemByQueryUseCase("laptop") } returns Resource.Success(testSearchItems)
        // When
        viewModel.onEvent(SearchEvent.SearchByQuery("laptop"))
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
    }
}
