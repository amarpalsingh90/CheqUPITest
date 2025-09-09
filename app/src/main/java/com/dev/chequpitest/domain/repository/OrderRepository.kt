package com.dev.chequpitest.domain.repository

import com.dev.chequpitest.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun createOrder(order: Order): Result<Unit>
    suspend fun updateOrder(order: Order): Result<Unit>
    suspend fun getOrderById(orderId: String): Order?
    fun getAllOrders(): Flow<List<Order>>
    fun getOrdersByUserId(userId: String): Flow<List<Order>>
}
