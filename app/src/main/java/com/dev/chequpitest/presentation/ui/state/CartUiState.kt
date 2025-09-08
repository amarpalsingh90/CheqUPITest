package com.dev.chequpitest.presentation.ui.state

import com.dev.chequpitest.domain.model.Cart

sealed class CartUiState {
    object Loading : CartUiState()
    data class Success(val cart: Cart) : CartUiState()
    data class Error(val message: String) : CartUiState()
}
