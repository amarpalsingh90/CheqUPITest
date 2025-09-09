package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(order: Order): Result<Unit> {
        return orderRepository.updateOrder(order)
    }
}
