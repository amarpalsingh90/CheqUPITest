package com.dev.chequpitest.presentation.ui.event

import com.dev.chequpitest.domain.model.Cart

sealed class PaymentEvent {
    data class StartPayment(val amount: Double) : PaymentEvent()

    // Other Event can we Implemented here as per requirement
}