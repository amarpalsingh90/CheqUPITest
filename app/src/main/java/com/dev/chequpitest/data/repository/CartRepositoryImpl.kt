package com.dev.chequpitest.data.repository

import com.dev.chequpitest.data.local.dao.CartDao
import com.dev.chequpitest.data.mapper.toDomain
import com.dev.chequpitest.data.mapper.toEntity
import com.dev.chequpitest.domain.model.Cart
import com.dev.chequpitest.domain.model.CartItem
import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCart(): Flow<Cart> {
        return cartDao.getAllCartItems().map { entities ->
            entities.toDomain()
        }
    }

    override suspend fun addToCart(product: Product): Result<Unit> {
        return try {
            val existingItem = cartDao.getCartItemByProductId(product.id)
            if (existingItem != null) {
                // Update quantity if item already exists
                val updatedItem = existingItem.copy(
                    quantity = existingItem.quantity + 1,
                    totalPrice = (existingItem.quantity + 1) * existingItem.productPrice
                )
                cartDao.updateCartItem(updatedItem)
            } else {
                // Add new item to cart
                val cartItem = CartItem(
                    id = UUID.randomUUID().toString(),
                    productId = product.id,
                    productTitle = product.title,
                    productPrice = product.price,
                    productThumbnail = product.thumbnail,
                    quantity = 1,
                    totalPrice = product.price
                )
                cartDao.insertCartItem(cartItem.toEntity())
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromCart(productId: Int): Result<Unit> {
        return try {
            cartDao.deleteCartItemByProductId(productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int): Result<Unit> {
        return try {
            val existingItem = cartDao.getCartItemByProductId(productId)
            if (existingItem != null) {
                if (quantity <= 0) {
                    cartDao.deleteCartItemByProductId(productId)
                } else {
                    val updatedItem = existingItem.copy(
                        quantity = quantity,
                        totalPrice = quantity * existingItem.productPrice
                    )
                    cartDao.updateCartItem(updatedItem)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearCart(): Result<Unit> {
        return try {
            cartDao.clearCart()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCartItemCount(): Int {
        return cartDao.getCartItemCount()
    }
}
