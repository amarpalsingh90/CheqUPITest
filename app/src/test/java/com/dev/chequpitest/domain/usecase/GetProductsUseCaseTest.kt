package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.CheqUpiProduct
import com.dev.chequpitest.domain.model.Dimensions
import com.dev.chequpitest.domain.model.Meta
import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.model.Review
import com.dev.chequpitest.domain.repository.ProductRepository
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
        val expectedProducts = CheqUpiProduct(
            limit = 10,
            products = listOf(
                Product(
                    availabilityStatus = "In Stock",
                    brand = "Test Brand",
                    category = "test",
                    description = "Test Description",
                    dimensions = Dimensions(10.0, 20.0, 30.0),
                    discountPercentage = 10.0,
                    id = 1,
                    images = listOf("https://example.com/test.jpg"),
                    meta = Meta("123", "2023-01-01", "qr123", "2023-01-01"),
                    minimumOrderQuantity = 1,
                    price = 99.99,
                    rating = 4.5,
                    returnPolicy = "30 days",
                    reviews = listOf(
                        Review("Great product", "2023-01-01", 5, "test@example.com", "Test User")
                    ),
                    shippingInformation = "Free shipping",
                    sku = "TEST123",
                    stock = 100,
                    tags = listOf("test", "sample"),
                    thumbnail = "https://example.com/test.jpg",
                    title = "Test Product",
                    warrantyInformation = "1 year",
                    weight = 500
                )
            ),
            skip = 0,
            total = 1
        )
        whenever(productRepository.getProducts()).thenReturn(Result.success(expectedProducts))

        // When
        val result = getProductsUseCase()

        // Then
        assertEquals(Result.success(expectedProducts), result)
    }
}
