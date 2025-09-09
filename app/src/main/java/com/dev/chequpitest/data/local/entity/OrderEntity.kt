package com.dev.chequpitest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.dev.chequpitest.domain.model.CartItem
import com.dev.chequpitest.domain.model.OrderStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "orders")
@TypeConverters(OrderConverters::class)
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val amount: Double,
    val dateTime: Long,
    val status: OrderStatus,
    val itemsJson: String, // Store List<CartItem> as JSON string
    val razorpayPaymentId: String? = null
)

class OrderConverters {
    @TypeConverter
    fun fromCartItemList(value: List<CartItem>): String {
        val gson = Gson()
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCartItemList(value: String): List<CartItem> {
        val gson = Gson()
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): String {
        return value.name
    }

    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus {
        return OrderStatus.valueOf(value)
    }
}
