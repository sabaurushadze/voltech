package com.tbc.home.presentation.screen.home

import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyViewedByQuantityUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.home.domain.model.CategoryItem
import com.tbc.home.domain.usecase.GetCategoriesUseCase
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
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : CoroutineTestRule() {

    private val testUser = User(uid = "user1", name = "Test", photoUrl = null)
    private val testCategoryItem = CategoryItem(
        id = 1,
        category = Category.GPU,
        image = "img.png",
    )
    private val testRecently = com.tbc.core.domain.model.recently_viewed.Recently(
        id = 1,
        uid = "user1",
        itemId = 10,
    )
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

    private val getCategoriesUseCase = mockk<GetCategoriesUseCase>()
    private val getRecentlyViewedByQuantityUseCase = mockk<GetRecentlyViewedByQuantityUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>()
    private val getItemsByIdsUseCase = mockk<GetItemsByIdsUseCase>()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            getCategoriesUseCase = getCategoriesUseCase,
            getRecentlyViewedByQuantityUseCase = getRecentlyViewedByQuantityUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
        )
    }

    @Test
    fun `initial state is correct`() {

        // Given
        // When
        // Then
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertTrue(viewModel.state.value.categoryList.isEmpty())
        assertTrue(viewModel.state.value.recentlyItemsId.isEmpty())
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.isLoadingCategories)
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
    }

    @Test
    fun `onEvent GetCategories success loads categories`() = runTest(testDispatcher) {

        // Given
        coEvery { getCategoriesUseCase() } returns Resource.Success(listOf(testCategoryItem))
        // When
        viewModel.onEvent(HomeEvent.GetCategories)
        advanceUntilIdle()
        // Then
        assertEquals(1, viewModel.state.value.categoryList.size)
        assertEquals(Category.GPU, viewModel.state.value.categoryList[0].category)
        assertFalse(viewModel.state.value.isLoadingCategories)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetCategories failure NO_CONNECTION sets showNoConnectionError true`() = runTest(testDispatcher) {

        // Given
        coEvery { getCategoriesUseCase() } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(HomeEvent.GetCategories)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.categoryList.isEmpty())
        assertTrue(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoadingCategories)
    }

    @Test
    fun `onEvent GetCategories failure other sets showNoConnectionError false`() = runTest(testDispatcher) {

        // Given
        coEvery { getCategoriesUseCase() } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(HomeEvent.GetCategories)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.categoryList.isEmpty())
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoadingCategories)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when user is null does not load`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns null
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        coVerify(exactly = 0) { getRecentlyViewedByQuantityUseCase(any()) }
    }

    @Test
    fun `onEvent GetRecentlyViewedItems success loads recently viewed items`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertEquals(listOf(10), viewModel.state.value.recentlyItemsId)
        assertEquals(1, viewModel.state.value.recentlyViewedItems.size)
        assertEquals("Item", viewModel.state.value.recentlyViewedItems[0].title)
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
        assertFalse(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getRecentlyViewedByQuantityUseCase fails NO_CONNECTION`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertTrue(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getRecentlyViewedByQuantityUseCase fails other sets showNoConnectionError false`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getItemsByIdsUseCase fails NO_CONNECTION`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertEquals(listOf(10), viewModel.state.value.recentlyItemsId)
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertTrue(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems when getItemsByIdsUseCase fails other sets showNoConnectionError false`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Failure(DataError.Network.TIMEOUT)
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertEquals(listOf(10), viewModel.state.value.recentlyItemsId)
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.showNoConnectionError)
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
    }

    @Test
    fun `onEvent GetRecentlyViewedItems with empty recently list updates recentryItemsId`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Success(emptyList())
        coEvery { getItemsByIdsUseCase(emptyList()) } returns Resource.Success(emptyList())
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.state.value.recentlyItemsId.isEmpty())
        assertTrue(viewModel.state.value.recentlyViewedItems.isEmpty())
        assertFalse(viewModel.state.value.isLoadingRecentlyViewed)
    }

    @Test
    fun `GetCategories calls getCategoriesUseCase`() = runTest(testDispatcher) {

        // Given
        coEvery { getCategoriesUseCase() } returns Resource.Success(listOf(testCategoryItem))
        // When
        viewModel.onEvent(HomeEvent.GetCategories)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { getCategoriesUseCase() }
    }

    @Test
    fun `GetRecentlyViewedItems calls getRecentlyViewedByQuantityUseCase with user uid`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns testUser
        coEvery { getRecentlyViewedByQuantityUseCase("user1") } returns Resource.Success(listOf(testRecently))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(testFeedItem))
        // When
        viewModel.onEvent(HomeEvent.GetRecentlyViewedItems)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { getRecentlyViewedByQuantityUseCase("user1") }
        coVerify(exactly = 1) { getItemsByIdsUseCase(listOf(10)) }
    }
}
