package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return cartRepository.clearCart()
    }
}
