package com.dev.chequpitest.domain.model

data class Cart(
    val items: List<CartItem>,
    val totalItems: Int,
    val totalAmount: Double
)
