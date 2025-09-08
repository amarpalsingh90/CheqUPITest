package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.repository.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int): Result<Unit> {
        return cartRepository.removeFromCart(productId)
    }
}
