package com.dev.chequpitest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val id: String,
    val productId: Int,
    val productTitle: String,
    val productPrice: Double,
    val productThumbnail: String,
    val quantity: Int,
    val totalPrice: Double
)
