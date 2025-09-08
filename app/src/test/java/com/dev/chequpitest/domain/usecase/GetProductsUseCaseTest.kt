package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.model.Rating
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetProductsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getProductsUseCase = GetProductsUseCase(productRepository)
    }

    @Test
    fun `invoke should return products from repository`() = runTest {
        // Given
        val expectedProducts = listOf(
            Product(
                id = 1,
                title = "Test Product",
                price = 99.99,
                description = "Test Description",
                category = "test",
                image = "https://example.com/test.jpg",
                rating = Rating(rate = 4.5, count = 100)
            )
        )
        whenever(productRepository.getProducts()).thenReturn(flowOf(Result.success(expectedProducts)))

        // When
        val result = getProductsUseCase()

        // Then
        result.collect { result ->
            assertEquals(Result.success(expectedProducts), result)
        }
    }
}
