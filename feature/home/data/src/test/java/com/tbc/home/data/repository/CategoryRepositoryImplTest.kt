package com.tbc.home.data.repository

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.home.data.remote.dto.CategoryItemResponseDto
import com.tbc.home.data.remote.service.category.CategoryService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class CategoryRepositoryImplTest {

    private val categoryService = mockk<CategoryService>()
    private val responseHandler = ApiResponseHandler()

    private lateinit var repository: CategoryRepositoryImpl

    @Before
    fun setup() {
        repository = CategoryRepositoryImpl(
            categoryService = categoryService,
            responseHandler = responseHandler
        )
    }

    @Test
    fun `getCategories success returns mapped domain categories`() = runTest {

        // Given
        val dto = CategoryItemResponseDto(id = 1, category = "GPU", image = "img.png")
        coEvery { categoryService.getCategories() } returns Response.success(listOf(dto))
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Success)
        val categories = (result as Resource.Success).data
        assertEquals(1, categories.size)
        assertEquals(1, categories[0].id)
        assertEquals(Category.GPU, categories[0].category)
        assertEquals("img.png", categories[0].image)
    }

    @Test
    fun `getCategories success with multiple categories returns all mapped`() = runTest {

        // Given
        val dtos = listOf(
            CategoryItemResponseDto(id = 1, category = "GPU", image = "gpu.png"),
            CategoryItemResponseDto(id = 2, category = "CPU", image = "cpu.png"),
        )
        coEvery { categoryService.getCategories() } returns Response.success(dtos)
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Success)
        val categories = (result as Resource.Success).data
        assertEquals(2, categories.size)
        assertEquals(Category.GPU, categories[0].category)
        assertEquals(Category.CPU, categories[1].category)
    }

    @Test
    fun `getCategories failure NO_CONNECTION when service throws IOException`() = runTest {

        // Given
        coEvery { categoryService.getCategories() } throws IOException("No connection")
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(DataError.Network.NO_CONNECTION, (result as Resource.Failure).error)
    }

    @Test
    fun `getCategories failure TIMEOUT when service throws SocketTimeoutException`() = runTest {

        // Given
        coEvery { categoryService.getCategories() } throws SocketTimeoutException("Timeout")
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(DataError.Network.TIMEOUT, (result as Resource.Failure).error)
    }

    @Test
    fun `getCategories failure NOT_FOUND when service returns 404`() = runTest {

        // Given
        val errorBody = "{}".toResponseBody("application/json".toMediaType())
        coEvery { categoryService.getCategories() } returns Response.error(404, errorBody)
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(DataError.Network.NOT_FOUND, (result as Resource.Failure).error)
    }

    @Test
    fun `getCategories failure BAD_REQUEST when service returns 400`() = runTest {

        // Given
        val errorBody = "{}".toResponseBody("application/json".toMediaType())
        coEvery { categoryService.getCategories() } returns Response.error(400, errorBody)
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Failure)
        assertEquals(DataError.Network.BAD_REQUEST, (result as Resource.Failure).error)
    }

    @Test
    fun `getCategories success with invalid category defaults to GPU`() = runTest {

        // Given
        val dto = CategoryItemResponseDto(id = 1, category = "INVALID_CAT", image = null)
        coEvery { categoryService.getCategories() } returns Response.success(listOf(dto))
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Success)
        assertEquals(Category.GPU, (result as Resource.Success).data[0].category)
    }

    @Test
    fun `getCategories success with empty list returns empty`() = runTest {

        // Given
        coEvery { categoryService.getCategories() } returns Response.success(emptyList())
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Success)
        assertTrue((result as Resource.Success).data.isEmpty())
    }

    @Test
    fun `getCategories success maps all Category enum values`() = runTest {

        // Given
        val dtos = listOf(
            CategoryItemResponseDto(id = 1, category = "MOTHERBOARD", image = null),
            CategoryItemResponseDto(id = 2, category = "RAM", image = null),
            CategoryItemResponseDto(id = 3, category = "SSD", image = null),
        )
        coEvery { categoryService.getCategories() } returns Response.success(dtos)
        // When
        val result = repository.getCategories()
        // Then
        assertTrue(result is Resource.Success)
        val categories = (result as Resource.Success).data
        assertEquals(Category.MOTHERBOARD, categories[0].category)
        assertEquals(Category.RAM, categories[1].category)
        assertEquals(Category.SSD, categories[2].category)
    }
}
