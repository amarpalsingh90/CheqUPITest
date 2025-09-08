package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int): Result<Unit> {
        return cartRepository.updateQuantity(productId, quantity)
    }
}
