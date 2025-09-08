package com.dev.chequpitest.presentation.ui.state

import com.dev.chequpitest.domain.model.CheqUpiProduct

sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: CheqUpiProduct) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}
