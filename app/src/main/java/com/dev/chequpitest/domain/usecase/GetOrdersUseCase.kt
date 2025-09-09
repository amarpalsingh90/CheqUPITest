package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(): Flow<List<Order>> {
        return orderRepository.getAllOrders()
    }
}
