package com.dev.chequpitest.presentation.ui.state

import com.dev.chequpitest.domain.model.Order

sealed class OrderHistoryUiState {
    object Loading : OrderHistoryUiState()
    data class Success(val orders: List<Order>) : OrderHistoryUiState()
    data class Error(val message: String) : OrderHistoryUiState()
    object Empty : OrderHistoryUiState()
}