package com.dev.chequpitest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.chequpitest.domain.usecase.GetProductsUseCase
import com.dev.chequpitest.presentation.ui.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            getProductsUseCase().fold(
                onSuccess = { products ->
                    _uiState.value = ProductUiState.Success(products)
                },
                onFailure = { exception ->
                    _uiState.value = ProductUiState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }

    fun refreshProducts() {
        loadProducts()
    }
}
