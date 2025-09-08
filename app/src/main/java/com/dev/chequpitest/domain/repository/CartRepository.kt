package com.dev.chequpitest.domain.repository

import com.dev.chequpitest.domain.model.Cart
import com.dev.chequpitest.domain.model.CartItem
import com.dev.chequpitest.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<Cart>
    suspend fun addToCart(product: Product): Result<Unit>
    suspend fun removeFromCart(productId: Int): Result<Unit>
    suspend fun updateQuantity(productId: Int, quantity: Int): Result<Unit>
    suspend fun clearCart(): Result<Unit>
    suspend fun getCartItemCount(): Int
}
