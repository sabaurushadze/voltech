package com.tbc.selling.presentation.screen.my_items

import app.cash.turbine.test
import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.usecase.feed.GetItemsByUidUseCase
import com.tbc.search.domain.usecase.feed.UpdateItemStatusUseCase
import com.tbc.selling.domain.usecase.selling.my_items.CheckUserItemAmountUseCase
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
class MyItemsViewModelTest : CoroutineTestRule() {

    private val testUser = User(uid = "user1", name = "John", photoUrl = "photo.png")
    private val testFeedItem = FeedItem(
        id = 10,
        uid = "user1",
        title = "My Item",
        category = Category.GPU,
        condition = Condition.NEW,
        price = 100.0,
        images = listOf("img1"),
        quantity = 1,
        location = Location.DIDI_DIGHOMI,
        userDescription = "desc",
        active = true,
    )

    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val getItemsByUidUseCase = mockk<GetItemsByUidUseCase>()
    private val checkUserItemAmountUseCase = mockk<CheckUserItemAmountUseCase>()
    private val updateItemStatusUseCase = mockk<UpdateItemStatusUseCase>()

    private lateinit var viewModel: MyItemsViewModel

    @Before
    fun setup() {
        every { getCurrentUserUseCase() } returns testUser
        every { checkUserItemAmountUseCase(any()) } answers { firstArg<Int>() < 5 }
        coEvery { getItemsByUidUseCase("user1") } returns Resource.Success(listOf(testFeedItem))
        coEvery { updateItemStatusUseCase(any(), any()) } returns Resource.Success(Unit)
        viewModel = MyItemsViewModel(
            getCurrentUserUseCase = getCurrentUserUseCase,
            getItemsByUidUseCase = getItemsByUidUseCase,
            checkUserItemAmountUseCase = checkUserItemAmountUseCase,
            updateItemStatusUseCase = updateItemStatusUseCase,
        )
    }

    @Test
    fun `initial state loads user and items`() = runTest(testDispatcher) {
        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user?.uid)
        assertEquals("John", viewModel.state.value.user?.name)
        assertEquals(1, viewModel.state.value.myItems.size)
        assertEquals(10, viewModel.state.value.myItems[0].id)
        assertEquals("My Item", viewModel.state.value.myItems[0].title)
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.editModeOn)
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertTrue(viewModel.state.value.userCanAddItem)
    }

    @Test
    fun `onEvent GetMyItems when user is null does nothing`() = runTest(testDispatcher) {
        // Given
        every { getCurrentUserUseCase() } returns null
        viewModel = MyItemsViewModel(
            getCurrentUserUseCase = getCurrentUserUseCase,
            getItemsByUidUseCase = getItemsByUidUseCase,
            checkUserItemAmountUseCase = checkUserItemAmountUseCase,
            updateItemStatusUseCase = updateItemStatusUseCase,
        )
        advanceUntilIdle()
        // When
        viewModel.onEvent(MyItemsEvent.GetMyItems)
        advanceUntilIdle()
        // Then
        assertNull(viewModel.state.value.user)
        assertTrue(viewModel.state.value.myItems.isEmpty())
    }

    @Test
    fun `onEvent GetMyItems when getItemsByUidUseCase returns NO_CONNECTION shows error`() = runTest(testDispatcher) {
        // Given - override so next call fails (init already succeeded)
        advanceUntilIdle()
        coEvery { getItemsByUidUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(MyItemsEvent.GetMyItems)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetMyItems when getItemsByUidUseCase returns other error clears loading`() = runTest(testDispatcher) {
        // Given - override so next call fails
        advanceUntilIdle()
        coEvery { getItemsByUidUseCase("user1") } returns Resource.Failure(DataError.Network.UNKNOWN)
        // When
        viewModel.onEvent(MyItemsEvent.GetMyItems)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetMyItems when getItemsByUidUseCase returns empty list`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getItemsByUidUseCase("user1") } returns Resource.Success(emptyList())
        every { checkUserItemAmountUseCase(0) } returns true
        // When
        viewModel.onEvent(MyItemsEvent.GetMyItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.myItems.isEmpty())
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent CanUserPostItems updates userCanAddItem`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        coEvery { getItemsByUidUseCase("user1") } returns Resource.Success(List(5) { testFeedItem.copy(id = it) })
        every { checkUserItemAmountUseCase(5) } returns false
        // When
        viewModel.onEvent(MyItemsEvent.CanUserPostItems)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.userCanAddItem)
    }

    @Test
    fun `onEvent NavigateToItemDetails emits NavigateToItemDetails side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(MyItemsEvent.NavigateToItemDetails(id = 10))
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(MyItemsSideEffect.NavigateToItemDetails::class, effect::class)
            assertEquals(10, (effect as MyItemsSideEffect.NavigateToItemDetails).id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent NavigateToAddItem emits NavigateToAddItem side effect`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(MyItemsEvent.NavigateToAddItem)
            runCurrent()
            // Then
            val effect = awaitItem()
            assertEquals(MyItemsSideEffect.NavigateToAddItem::class, effect::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent EditModeOn sets editModeOn true`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        // When
        viewModel.onEvent(MyItemsEvent.EditModeOn)
        // Then
        assertTrue(viewModel.state.value.editModeOn)
    }

    @Test
    fun `onEvent EditModeOff clears selection and turns off edit mode`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(MyItemsEvent.EditModeOn)
        viewModel.onEvent(MyItemsEvent.ToggleItemForDeletion(id = 10))
        assertTrue(viewModel.state.value.myItems[0].isSelected)
        // When
        viewModel.onEvent(MyItemsEvent.EditModeOff)
        // Then
        assertFalse(viewModel.state.value.editModeOn)
        assertFalse(viewModel.state.value.myItems.any { it.isSelected })
    }

    @Test
    fun `onEvent ToggleItemForDeletion toggles item selection`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(MyItemsEvent.EditModeOn)
        assertFalse(viewModel.state.value.myItems[0].isSelected)
        // When
        viewModel.onEvent(MyItemsEvent.ToggleItemForDeletion(id = 10))
        // Then
        assertTrue(viewModel.state.value.myItems[0].isSelected)
        // When - toggle again
        viewModel.onEvent(MyItemsEvent.ToggleItemForDeletion(id = 10))
        // Then
        assertFalse(viewModel.state.value.myItems[0].isSelected)
    }

    @Test
    fun `onEvent ToggleSelectAll selects all when selectAll true`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        assertFalse(viewModel.state.value.allSelected)
        // When
        viewModel.onEvent(MyItemsEvent.ToggleSelectAll(selectAll = true))
        // Then
        assertTrue(viewModel.state.value.myItems.all { it.isSelected })
        assertTrue(viewModel.state.value.allSelected)
    }

    @Test
    fun `onEvent ToggleSelectAll deselects all when selectAll false`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(MyItemsEvent.ToggleSelectAll(selectAll = true))
        assertTrue(viewModel.state.value.allSelected)
        // When
        viewModel.onEvent(MyItemsEvent.ToggleSelectAll(selectAll = false))
        // Then
        assertFalse(viewModel.state.value.myItems.any { it.isSelected })
    }

    @Test
    fun `onEvent DeleteFavoriteItemById calls updateItemStatusUseCase and reloads`() = runTest(testDispatcher) {
        // Given
        advanceUntilIdle()
        viewModel.onEvent(MyItemsEvent.EditModeOn)
        viewModel.onEvent(MyItemsEvent.ToggleItemForDeletion(id = 10))
        // When
        viewModel.onEvent(MyItemsEvent.DeleteFavoriteItemById)
        advanceUntilIdle()
        // Then
        coVerify { updateItemStatusUseCase(id = 10, itemStatus = match { it.active == false }) }
        coVerify(exactly = 3) { getItemsByUidUseCase("user1") }
    }
}
