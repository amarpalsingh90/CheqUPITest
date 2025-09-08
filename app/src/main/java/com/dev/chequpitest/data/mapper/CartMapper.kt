package com.dev.chequpitest.data.mapper

import com.dev.chequpitest.data.local.entity.CartItemEntity
import com.dev.chequpitest.domain.model.Cart
import com.dev.chequpitest.domain.model.CartItem

fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        id = id,
        productId = productId,
        productTitle = productTitle,
        productPrice = productPrice,
        productThumbnail = productThumbnail,
        quantity = quantity,
        totalPrice = totalPrice
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        productId = productId,
        productTitle = productTitle,
        productPrice = productPrice,
        productThumbnail = productThumbnail,
        quantity = quantity,
        totalPrice = totalPrice
    )
}

fun List<CartItemEntity>.toDomain(): Cart {
    val items = this.map { it.toDomain() }
    return Cart(
        items = items,
        totalItems = items.sumOf { it.quantity },
        totalAmount = items.sumOf { it.totalPrice }
    )
}
