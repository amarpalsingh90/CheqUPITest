package com.dev.chequpitest.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val isSignedIn: Boolean = true
)
