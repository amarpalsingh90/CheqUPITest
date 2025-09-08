package com.dev.chequpitest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.chequpitest.domain.model.Cart
import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.usecase.AddToCartUseCase
import com.dev.chequpitest.domain.usecase.ClearCartUseCase
import com.dev.chequpitest.domain.usecase.GetCartUseCase
import com.dev.chequpitest.domain.usecase.RemoveFromCartUseCase
import com.dev.chequpitest.domain.usecase.UpdateCartQuantityUseCase
import com.dev.chequpitest.presentation.ui.event.PaymentEvent
import com.dev.chequpitest.presentation.ui.state.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PaymentEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadCart()
    }


    fun onPayButtonClicked(totalAmount: Double)  {
        // Business logic or validations
        viewModelScope.launch {
            _uiEvent.emit(PaymentEvent.StartPayment(totalAmount))
        }
    }



    private fun loadCart() {
        viewModelScope.launch {
            getCartUseCase().collect { cart ->
                _uiState.value = CartUiState.Success(cart)
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product).fold(
                onSuccess = { /* Cart will be updated automatically via Flow */ },
                onFailure = { exception ->
                    _uiState.value = CartUiState.Error(exception.message ?: "Failed to add to cart")
                }
            )
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            removeFromCartUseCase(productId).fold(
                onSuccess = { /* Cart will be updated automatically via Flow */ },
                onFailure = { exception ->
                    _uiState.value = CartUiState.Error(exception.message ?: "Failed to remove from cart")
                }
            )
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            updateCartQuantityUseCase(productId, quantity).fold(
                onSuccess = { /* Cart will be updated automatically via Flow */ },
                onFailure = { exception ->
                    _uiState.value = CartUiState.Error(exception.message ?: "Failed to update quantity")
                }
            )
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase().fold(
                onSuccess = { /* Cart will be updated automatically via Flow */ },
                onFailure = { exception ->
                    _uiState.value = CartUiState.Error(exception.message ?: "Failed to clear cart")
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = CartUiState.Loading
        loadCart()
    }
}
