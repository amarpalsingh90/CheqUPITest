package com.dev.chequpitest.data.mapper

import com.dev.chequpitest.data.remote.dto.CheqUpiProductDto
import com.dev.chequpitest.data.remote.dto.DimensionsDto
import com.dev.chequpitest.data.remote.dto.MetaDto
import com.dev.chequpitest.data.remote.dto.ProductDto
import com.dev.chequpitest.data.remote.dto.ReviewDto
import com.dev.chequpitest.domain.model.CheqUpiProduct
import com.dev.chequpitest.domain.model.Dimensions
import com.dev.chequpitest.domain.model.Meta
import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.model.Review

fun CheqUpiProductDto.toDomain(): CheqUpiProduct {
    return CheqUpiProduct(
        limit = limit,
        products = products.map { it.toDomain() },
        skip = skip,
        total = total
    )
}

fun ProductDto.toDomain(): Product {
    return Product(
        availabilityStatus = availabilityStatus,
        brand = brand,
        category = category,
        description = description,
        dimensions = dimensions.toDomain(),
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        meta = meta.toDomain(),
        minimumOrderQuantity = minimumOrderQuantity,
        price = price,
        rating = rating,
        returnPolicy = returnPolicy,
        reviews = reviews.map { it.toDomain() },
        shippingInformation = shippingInformation,
        sku = sku,
        stock = stock,
        tags = tags,
        thumbnail = thumbnail,
        title = title,
        warrantyInformation = warrantyInformation,
        weight = weight
    )
}

fun DimensionsDto.toDomain(): Dimensions {
    return Dimensions(
        depth = depth,
        height = height,
        width = width
    )
}

fun MetaDto.toDomain(): Meta {
    return Meta(
        barcode = barcode,
        createdAt = createdAt,
        qrCode = qrCode,
        updatedAt = updatedAt
    )
}

fun ReviewDto.toDomain(): Review {
    return Review(
        comment = comment,
        date = date,
        rating = rating,
        reviewerEmail = reviewerEmail,
        reviewerName = reviewerName
    )
}
