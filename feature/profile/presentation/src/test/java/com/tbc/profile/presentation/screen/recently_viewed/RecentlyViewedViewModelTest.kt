package com.tbc.profile.presentation.screen.recently_viewed

import app.cash.turbine.test
import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.recently_viewed.DeleteRecentlyViewedByIdUseCase
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
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
class RecentlyViewedViewModelTest : CoroutineTestRule() {

    private val testUser = User(uid = "user1", name = "John", photoUrl = "photo.png")
    private val testRecently = Recently(id = 1, uid = "user1", itemId = 10)
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

    private val getRecentlyUseCase = mockk<GetRecentlyUseCase>()
    private val getItemsByIdsUseCase = mockk<GetItemsByIdsUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val deleteRecentlyViewedByIdUseCase = mockk<DeleteRecentlyViewedByIdUseCase>()

    private lateinit var viewModel: RecentlyViewedViewModel

    @Before
    fun setup() {
        every { getCurrentUserUseCase() } returns testUser
        viewModel = RecentlyViewedViewModel(
            getRecentlyUseCase = getRecentlyUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            deleteRecentlyViewedByIdUseCase = deleteRecentlyViewedByIdUseCase,
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
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.editModeOn)
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
    }

    @Test
    fun `onEvent GetRecentlyViewedItems success loads items`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        // When
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertEquals(1, viewModel.state.value.recentlyViewedItems.size)
        assertEquals(10, viewModel.state.value.recentlyViewedItems[0].id)
        assertEquals(1, viewModel.state.value.recentlyViewedItems[0].recentlyId)
        assertEquals("Item", viewModel.state.value.recentlyViewedItems[0].title)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when user is null does nothing`() = runTest(testDispatcher) {
        // Given
        every { getCurrentUserUseCase() } returns null
        viewModel = RecentlyViewedViewModel(
            getRecentlyUseCase = getRecentlyUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            deleteRecentlyViewedByIdUseCase = deleteRecentlyViewedByIdUseCase,
        )
        advanceUntilIdle()
        // When
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then - getRecentlyUseCase never called
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getRecentlyUseCase returns NO_CONNECTION`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getItemsByIdsUseCase returns NO_CONNECTION`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getRecentlyUseCase returns empty list`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(emptyList())
        coEvery { getItemsByIdsUseCase(emptyList()) } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent NavigateBackToProfile emits NavigateBackToProfile side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(RecentlyViewedEvent.NavigateBackToProfile)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(RecentlyViewedSideEffect.NavigateBackToProfile::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateToItemDetails emits NavigateToItemDetails side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(RecentlyViewedEvent.NavigateToItemDetails(itemId = 10))
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(RecentlyViewedSideEffect.NavigateToItemDetails::class, effect::class)
            assertEquals(10, (effect as RecentlyViewedSideEffect.NavigateToItemDetails).itemId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent EditModeOn sets editModeOn true`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(RecentlyViewedEvent.EditModeOn)
        // Then
        assertTrue(viewModel.state.value.editModeOn)
    }

    @Test
    fun `onEvent EditModeOff clears selection and turns off edit mode`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        viewModel.onEvent(RecentlyViewedEvent.EditModeOn)
        viewModel.onEvent(RecentlyViewedEvent.ToggleItemForDeletion(recentlyId = 1))
        assertTrue(viewModel.state.value.recentlyViewedItems[0].isSelected)
        // When
        viewModel.onEvent(RecentlyViewedEvent.EditModeOff)
        // Then
        assertFalse(viewModel.state.value.editModeOn)
        assertFalse(viewModel.state.value.recentlyViewedItems.any { it.isSelected })
    }

    @Test
    fun `onEvent ToggleItemForDeletion toggles item selection`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        assertFalse(viewModel.state.value.recentlyViewedItems[0].isSelected)
        // When
        viewModel.onEvent(RecentlyViewedEvent.ToggleItemForDeletion(recentlyId = 1))
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems[0].isSelected)
        // When - toggle again
        viewModel.onEvent(RecentlyViewedEvent.ToggleItemForDeletion(recentlyId = 1))
        // Then
        assertFalse(viewModel.state.value.recentlyViewedItems[0].isSelected)
    }

    @Test
    fun `onEvent ToggleSelectAll selects all when selectAll true`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        assertFalse(viewModel.state.value.allSelected)
        // When
        viewModel.onEvent(RecentlyViewedEvent.ToggleSelectAll(selectAll = true))
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.all { it.isSelected })
        assertTrue(viewModel.state.value.allSelected)
    }

    @Test
    fun `onEvent ToggleSelectAll deselects all when selectAll false`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        viewModel.onEvent(RecentlyViewedEvent.ToggleSelectAll(selectAll = true))
        assertTrue(viewModel.state.value.allSelected)
        // When
        viewModel.onEvent(RecentlyViewedEvent.ToggleSelectAll(selectAll = false))
        // Then
        assertFalse(viewModel.state.value.recentlyViewedItems.any { it.isSelected })
    }

    @Test
    fun `onEvent DeleteRecentlyItemById deletes selected and reloads`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getRecentlyUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        coEvery { deleteRecentlyViewedByIdUseCase(1) } returns Resource.Success(Unit)
        viewModel.onEvent(RecentlyViewedEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        viewModel.onEvent(RecentlyViewedEvent.ToggleItemForDeletion(recentlyId = 1))
        // When
        viewModel.onEvent(RecentlyViewedEvent.DeleteRecentlyItemById)
        advanceUntilIdle()
        // Then
        coVerify { deleteRecentlyViewedByIdUseCase(1) }
        coVerify(exactly = 2) { getRecentlyUseCase("user1") }
    }
}
