package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.Cart
import com.dev.chequpitest.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Cart> {
        return cartRepository.getCart()
    }
}
