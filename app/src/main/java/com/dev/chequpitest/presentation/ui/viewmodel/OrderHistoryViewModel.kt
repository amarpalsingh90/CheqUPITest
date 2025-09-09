package com.dev.chequpitest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.model.OrderStatus
import com.dev.chequpitest.domain.usecase.GetOrdersUseCase
import com.dev.chequpitest.domain.usecase.UpdateOrderUseCase
import com.dev.chequpitest.presentation.ui.state.OrderHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<OrderHistoryUiState>(OrderHistoryUiState.Loading)
    val uiState: StateFlow<OrderHistoryUiState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            getOrdersUseCase()
                .onStart { _uiState.value = OrderHistoryUiState.Loading }
                .map { orders ->
                    if (orders.isEmpty()) {
                        OrderHistoryUiState.Empty
                    } else {
                        OrderHistoryUiState.Success(orders)
                    }
                }
                .catch { e ->
                    _uiState.value = OrderHistoryUiState.Error(e.message ?: "Unknown error")
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    fun updateOrderToSuccess(order: Order, razorpayPaymentId: String?) {
        viewModelScope.launch {
            val updatedOrder = order.copy(
                status = OrderStatus.ORDER_PLACED_SUCCESSFULLY,
                razorpayPaymentId = razorpayPaymentId
            )
            updateOrderUseCase(updatedOrder)
        }
    }

    fun updateOrderToFailed(order: Order) {
        viewModelScope.launch {
            val updatedOrder = order.copy(
                status = OrderStatus.ORDER_FAILED
            )
            updateOrderUseCase(updatedOrder)
        }
    }
}
