package com.dev.chequpitest.data.mapper

import com.dev.chequpitest.data.local.entity.OrderEntity
import com.dev.chequpitest.data.local.entity.OrderConverters
import com.dev.chequpitest.domain.model.Order

fun OrderEntity.toDomain(): Order {
    val converters = OrderConverters()
    return Order(
        id = id,
        userId = userId,
        amount = amount,
        dateTime = dateTime,
        status = status,
        items = converters.toCartItemList(itemsJson),
        razorpayPaymentId = razorpayPaymentId
    )
}

fun Order.toEntity(): OrderEntity {
    val converters = OrderConverters()
    return OrderEntity(
        id = id,
        userId = userId,
        amount = amount,
        dateTime = dateTime,
        status = status,
        itemsJson = converters.fromCartItemList(items),
        razorpayPaymentId = razorpayPaymentId
    )
}
