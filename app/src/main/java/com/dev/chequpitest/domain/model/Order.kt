package com.dev.chequpitest.domain.model

data class Order(
    val id: String,
    val userId: String,
    val amount: Double,
    val dateTime: Long, // Timestamp
    val status: OrderStatus,
    val items: List<CartItem> = emptyList(),
    val razorpayPaymentId: String? = null
)

enum class OrderStatus {
    IN_PROGRESS,
    ORDER_PLACED_SUCCESSFULLY,
    ORDER_FAILED
}
