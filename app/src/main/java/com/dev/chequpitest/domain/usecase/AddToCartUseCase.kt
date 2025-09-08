package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(product: Product): Result<Unit> {
        return cartRepository.addToCart(product)
    }
}
