package com.dev.chequpitest.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.chequpitest.data.local.dao.UserDao
import com.dev.chequpitest.data.mapper.toDomain
import com.dev.chequpitest.domain.model.Order
import com.dev.chequpitest.domain.model.OrderStatus
import com.dev.chequpitest.domain.model.Product
import com.dev.chequpitest.domain.model.User
import com.dev.chequpitest.domain.usecase.AddToCartUseCase
import com.dev.chequpitest.domain.usecase.ClearCartUseCase
import com.dev.chequpitest.domain.usecase.CreateOrderUseCase
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val userDao: UserDao
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
            try {
                // Get current user data
                val currentUser = getCurrentUser()
                
                // Get current cart data
                val cart = getCartUseCase().first()
                
                // Create order before payment
                val order = Order(
                    id = UUID.randomUUID().toString(),
                    userId = currentUser?.id ?: "anonymous",
                    amount = totalAmount,
                    dateTime = System.currentTimeMillis(),
                    status = OrderStatus.IN_PROGRESS,
                    items = cart.items
                )
                
                // Save order to database
                createOrderUseCase(order)
                
                // Emit payment event with order
                _uiEvent.emit(PaymentEvent.StartPayment(totalAmount, currentUser, order))
            } catch (e: Exception) {
                // Handle error - could emit error event or show error state
                Log.e("CartViewModel", "Error creating order: ${e.message}", e)
            }
        }
    }

    private suspend fun getCurrentUser(): User? {
        return try {
            userDao.getCurrentUser().first()?.toDomain()
        } catch (e: Exception) {
            null
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
