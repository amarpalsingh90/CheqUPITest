package com.dev.chequpitest.domain.model

data class CartItem(
    val id: String,
    val productId: Int,
    val productTitle: String,
    val productPrice: Double,
    val productThumbnail: String,
    val quantity: Int,
    val totalPrice: Double
)
