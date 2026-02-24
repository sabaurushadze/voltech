package com.tbc.search.presentation.screen.add_to_cart

import app.cash.turbine.test
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core_testing.test.CoroutineTestRule
import com.tbc.resource.R
import com.tbc.search.domain.usecase.cart.DeleteCartItemUseCase
import com.tbc.search.domain.usecase.cart.GetCartItemsUseCase
import com.tbc.search.domain.usecase.feed.GetItemsByIdsUseCase
import com.tbc.search.presentation.test.TestFixtures
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import io.mockk.coEvery
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
class AddToCartViewModelTest : CoroutineTestRule() {

    private val getCartItemsUseCase = mockk<GetCartItemsUseCase>()
    private val getCurrentUserUseCase = mockk<GetCurrentUserUseCase>().apply {
        every { this@apply() } returns TestFixtures.user
    }
    private val getItemsByIdsUseCase = mockk<GetItemsByIdsUseCase>()
    private val deleteCartItemUseCase = mockk<DeleteCartItemUseCase>()
    private val getSellersUseCase = mockk<GetSellersUseCase>()

    private lateinit var viewModel: AddToCartViewModel

    @Before
    fun setup() {
        viewModel = AddToCartViewModel(
            getCartItemsUseCase = getCartItemsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            deleteCartItemUseCase = deleteCartItemUseCase,
            getSellersUseCase = getSellersUseCase,
        )
    }

    @Test
    fun `initial state has user after getCurrentUser`() = runTest(testDispatcher) {

        // Given
        // When
        advanceUntilIdle()
        // Then
        assertEquals("user1", viewModel.state.value.user?.uid)
        assertEquals("Test", viewModel.state.value.user?.name)
    }

    @Test
    fun `initial state when getCurrentUser returns null has no user`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns null
        val vm = AddToCartViewModel(
            getCartItemsUseCase = getCartItemsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            deleteCartItemUseCase = deleteCartItemUseCase,
            getSellersUseCase = getSellersUseCase,
        )
        advanceUntilIdle()
        // When
        // Then
        assertNull(vm.state.value.user)
    }

    @Test
    fun `onEvent GetCartItems loads cart items successfully`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getCartItemsUseCase("user1") } returns Resource.Success(listOf(TestFixtures.cart))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(TestFixtures.feedItemEmptyImages))
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponseZeroFeedback))
        // When
        viewModel.onEvent(AddToCartEvent.GetCartItems)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(1, viewModel.state.value.cartItems.size)
        assertEquals("Item", viewModel.state.value.cartItems.first().title)
        assertEquals(100.0, viewModel.state.value.subtotal, 0.01)
    }

    @Test
    fun `onEvent GetCartItems when user is null does nothing`() = runTest(testDispatcher) {

        // Given
        every { getCurrentUserUseCase() } returns null
        val vm = AddToCartViewModel(
            getCartItemsUseCase = getCartItemsUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase,
            getItemsByIdsUseCase = getItemsByIdsUseCase,
            deleteCartItemUseCase = deleteCartItemUseCase,
            getSellersUseCase = getSellersUseCase,
        )
        advanceUntilIdle()
        // When
        vm.onEvent(AddToCartEvent.GetCartItems)
        advanceUntilIdle()
        // Then
        assertTrue(vm.state.value.cartItems.isEmpty())
    }

    @Test
    fun `onEvent GetCartItems when getCartItems fails with NO_CONNECTION shows error`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getCartItemsUseCase("user1") } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(AddToCartEvent.GetCartItems)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent GetCartItems when getItemsByIds fails with NO_CONNECTION shows error`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getCartItemsUseCase("user1") } returns Resource.Success(listOf(TestFixtures.cart))
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Failure(DataError.Network.NO_CONNECTION)
        // When
        viewModel.onEvent(AddToCartEvent.GetCartItems)
        advanceUntilIdle()
        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.showNoConnectionError)
    }

    @Test
    fun `onEvent DeleteCartItems deletes and refreshes cart`() = runTest(testDispatcher) {

        // Given
        advanceUntilIdle()
        coEvery { getCartItemsUseCase("user1") } returnsMany listOf(
            Resource.Success(listOf(TestFixtures.cart)),
            Resource.Success(emptyList()),
        )
        coEvery { getItemsByIdsUseCase(listOf(10)) } returns Resource.Success(listOf(TestFixtures.feedItemEmptyImages))
        coEvery { getItemsByIdsUseCase(emptyList()) } returns Resource.Success(emptyList())
        coEvery { getSellersUseCase("seller1") } returns Resource.Success(listOf(TestFixtures.sellerResponseZeroFeedback))
        coEvery { deleteCartItemUseCase(1) } returns Resource.Success(Unit)
        viewModel.onEvent(AddToCartEvent.GetCartItems)
        advanceUntilIdle()
        // When
        viewModel.onEvent(AddToCartEvent.DeleteCartItems(1))
        advanceUntilIdle()
        // Then
        assertEquals(0, viewModel.state.value.cartItems.size)
    }

    @Test
    fun `onEvent BuyItem emits ShowSnackBar side effect`() = runTest(testDispatcher) {

        // Given
        viewModel.sideEffect.test {
            // When
            viewModel.onEvent(AddToCartEvent.BuyItem)
            runCurrent()
            // Then
            val sideEffect = awaitItem()
            assertEquals(AddToCartSideEffect.ShowSnackBar::class, sideEffect::class)
            assertEquals(R.string.this_service_not_work, (sideEffect as AddToCartSideEffect.ShowSnackBar).errorRes)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
