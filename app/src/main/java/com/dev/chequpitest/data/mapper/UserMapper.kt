package com.dev.chequpitest.data.mapper

import com.dev.chequpitest.data.local.entity.UserEntity
import com.dev.chequpitest.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phone = phone,
        profileImageUrl = profileImageUrl,
        isSignedIn = isSignedIn
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        profileImageUrl = profileImageUrl,
        isSignedIn = isSignedIn
    )
}
