package com.dev.chequpitest.data.repository

import com.dev.chequpitest.data.local.dao.OrderDao
import com.dev.chequpitest.data.mapper.toDomain
import com.dev.chequpitest.data.mapper.toEntity
import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun createOrder(order: Order): Result<Unit> {
        return try {
            orderDao.insertOrder(order.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOrder(order: Order): Result<Unit> {
        return try {
            orderDao.updateOrder(order.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return orderDao.getOrderById(orderId)?.toDomain()
    }

    override fun getAllOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getOrdersByUserId(userId: String): Flow<List<Order>> {
        return orderDao.getOrdersByUserId(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
