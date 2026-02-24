package com.tbc.profile.presentation.screen.watchlist

import app.cash.turbine.test
import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.model.favorite.Favorite
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.usecase.favorite.DeleteFavoriteItemByIdUseCase
import com.tbc.search.domain.usecase.favorite.GetFavoriteItemsUseCase
import com.tbc.search.domain.usecase.feed.GetItemsByIdsUseCase
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WatchlistViewModelTest : CoroutineTestRule() {

    private val testUser = User(uid = "user1", name = "John", photoUrl = "photo.png")
    private val testFavorite = Favorite(id = 1, uid = "user1", itemId = 10)
    private val testFeedItem = FeedItem(
        id = 10,
        uid = "seller1",
        title = "Item",
        category = Category.GPU,
        condition = Condition.NEW,
        price = 100.0,
        images = listOf("img1"),
        quantity = 1,
        location = Location.DIDI_DIGHOMI,
        userDescription = "desc",
        active = true,
    )

    private val getFavoriteItemsUseCase = mockk<GetFavoriteItemsUseCase>()
    private val deleteFavoriteItemByIdUseCase = mockk<DeleteFavoriteItemByIdUseCase>()
    private val getItemsByIdsUseCase = mockk<GetItemsByIdsUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()

    private lateinit var viewModel: WatchlistViewModel

    @Before
    fun setup() {
        every { getCurrentUserUseCase() } returns testUser
        viewModel = WatchlistViewModel(
            getFavoriteItemsUseCase = getFavoriteItemsUseCase,
            deleteFavoriteItemByIdUseCase = deleteFavoriteItemByIdUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
        )
    }

    @Test
    fun `initial state loads user`() = runTest(testDispatcher) {
        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user?.uid)
        assertEquals("John", viewModel.state.value.user?.name)
        assertFalse(viewModel.state.value.editModeOn)
        assertTrue(viewModel.state.value.favoriteItems.isEmpty())
    }

    @Test
    fun `onEvent GetFavoriteItems success loads items`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        // When
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        // Then
        assertEquals(1, viewModel.state.value.favoriteItems.size)
        assertEquals(10, viewModel.state.value.favoriteItems[0].id)
        assertEquals(1, viewModel.state.value.favoriteItems[0].favoriteId)
        assertEquals("Item", viewModel.state.value.favoriteItems[0].title)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetFavoriteItems when user is null does nothing`() = runTest(testDispatcher) {
        // Given
        every { getCurrentUserUseCase() } returns null
        viewModel = WatchlistViewModel(
            getFavoriteItemsUseCase = getFavoriteItemsUseCase,
            deleteFavoriteItemByIdUseCase = deleteFavoriteItemByIdUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
        )
        advanceUntilIdle()
        // When
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItems.isEmpty())
    }

    @Test
    fun `onEvent GetFavoriteItems when getFavoriteItemsUseCase returns NO_CONNECTION`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetFavoriteItems when getItemsByIdsUseCase returns NO_CONNECTION`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetFavoriteItems when getFavoriteItemsUseCase returns empty list`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.favoriteItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent NavigateBackToProfile emits NavigateBackToProfile side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(WatchlistEvent.NavigateBackToProfile)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(WatchlistSideEffect.NavigateBackToProfile::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateToItemDetails emits NavigateToItemDetails side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(WatchlistEvent.NavigateToItemDetails(itemId = 10))
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(WatchlistSideEffect.NavigateToItemDetails::class, effect::class)
            assertEquals(10, (effect as WatchlistSideEffect.NavigateToItemDetails).itemId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent EditModeOn sets editModeOn true`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(WatchlistEvent.EditModeOn)
        // Then
        assertTrue(viewModel.state.value.editModeOn)
    }

    @Test
    fun `onEvent EditModeOff clears selection and turns off edit mode`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        viewModel.onEvent(WatchlistEvent.EditModeOn)
        viewModel.onEvent(WatchlistEvent.ToggleItemForDeletion(favoriteId = 1))
        assertTrue(viewModel.state.value.favoriteItems[0].isSelected)
        // When
        viewModel.onEvent(WatchlistEvent.EditModeOff)
        // Then
        assertFalse(viewModel.state.value.editModeOn)
        assertFalse(viewModel.state.value.favoriteItems.any { it.isSelected })
    }

    @Test
    fun `onEvent ToggleItemForDeletion toggles item selection`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        assertFalse(viewModel.state.value.favoriteItems[0].isSelected)
        // When
        viewModel.onEvent(WatchlistEvent.ToggleItemForDeletion(favoriteId = 1))
        // Then
        assertTrue(viewModel.state.value.favoriteItems[0].isSelected)
        // When - toggle again
        viewModel.onEvent(WatchlistEvent.ToggleItemForDeletion(favoriteId = 1))
        // Then
        assertFalse(viewModel.state.value.favoriteItems[0].isSelected)
    }

    @Test
    fun `onEvent ToggleSelectAll selects all when selectAll true`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        assertFalse(viewModel.state.value.allSelected)
        // When
        viewModel.onEvent(WatchlistEvent.ToggleSelectAll(selectAll = true))
        // Then
        assertTrue(viewModel.state.value.favoriteItems.all { it.isSelected })
        assertTrue(viewModel.state.value.allSelected)
    }

    @Test
    fun `onEvent ToggleSelectAll deselects all when selectAll false`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        viewModel.onEvent(WatchlistEvent.ToggleSelectAll(selectAll = true))
        assertTrue(viewModel.state.value.allSelected)
        // When
        viewModel.onEvent(WatchlistEvent.ToggleSelectAll(selectAll = false))
        // Then
        assertFalse(viewModel.state.value.favoriteItems.any { it.isSelected })
    }

    @Test
    fun `onEvent DeleteFavoriteItemById deletes selected and reloads`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getFavoriteItemsUseCase("user1") } returns Resource.Success(listOf(testFavorite))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        coEvery { deleteFavoriteItemByIdUseCase(1) } returns Resource.Success(Unit)
        viewModel.onEvent(WatchlistEvent.GetFavoriteItems)
        advanceUntilIdle()
        viewModel.onEvent(WatchlistEvent.ToggleItemForDeletion(favoriteId = 1))
        // When
        viewModel.onEvent(WatchlistEvent.DeleteFavoriteItemById)
        advanceUntilIdle()
        // Then
        coVerify { deleteFavoriteItemByIdUseCase(1) }
        coVerify(exactly = 2) { getFavoriteItemsUseCase("user1") }
    }
}
