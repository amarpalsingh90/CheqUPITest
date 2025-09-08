package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.CheqUpiProduct
import com.dev.chequpitest.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Result<CheqUpiProduct> {
        return productRepository.getProducts()
    }
}
