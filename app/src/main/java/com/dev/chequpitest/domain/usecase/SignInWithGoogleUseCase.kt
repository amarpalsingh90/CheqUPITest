package com.dev.chequpitest.domain.usecase

import com.dev.chequpitest.domain.model.User
import com.dev.chequpitest.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.signInWithGoogle()
    }
}
