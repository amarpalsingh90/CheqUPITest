package com.dev.chequpitest.presentation.ui.state

import com.dev.chequpitest.domain.model.User

sealed class AuthUiState {
    object Loading : AuthUiState()
    object Unauthenticated : AuthUiState()
    data class Authenticated(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
