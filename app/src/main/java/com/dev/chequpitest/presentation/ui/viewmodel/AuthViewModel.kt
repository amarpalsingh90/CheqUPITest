package com.dev.chequpitest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.chequpitest.domain.usecase.GetCurrentUserUseCase
import com.dev.chequpitest.domain.usecase.SignInWithGoogleUseCase
import com.dev.chequpitest.domain.usecase.SignOutUseCase
import com.dev.chequpitest.presentation.ui.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                _uiState.value = if (user != null) {
                    AuthUiState.Authenticated(user)
                } else {
                    AuthUiState.Unauthenticated
                }
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            signInWithGoogleUseCase().fold(
                onSuccess = { user ->
                    _uiState.value = AuthUiState.Authenticated(user)
                },
                onFailure = { exception ->
                    _uiState.value = AuthUiState.Error(exception.message ?: "Sign in failed")
                }
            )
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase().fold(
                onSuccess = {
                    _uiState.value = AuthUiState.Unauthenticated
                },
                onFailure = { exception ->
                    _uiState.value = AuthUiState.Error(exception.message ?: "Sign out failed")
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = AuthUiState.Unauthenticated
    }
}
