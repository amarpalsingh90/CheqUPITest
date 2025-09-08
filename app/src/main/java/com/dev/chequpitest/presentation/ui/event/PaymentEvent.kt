package com.dev.chequpitest.presentation.ui.event

sealed class PaymentEvent {
    data class StartPayment(val amount: Double) : PaymentEvent()
}