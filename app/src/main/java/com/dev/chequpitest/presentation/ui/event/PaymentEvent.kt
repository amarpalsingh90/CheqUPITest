package com.dev.chequpitest.presentation.ui.event

import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.model.User

sealed class PaymentEvent {
    data class StartPayment(val amount: Double, val user: User?, val order: Order) : PaymentEvent()
}