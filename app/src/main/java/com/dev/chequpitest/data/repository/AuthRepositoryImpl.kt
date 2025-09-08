package com.dev.chequpitest.data.repository

import android.content.Context
import com.dev.chequpitest.data.auth.GoogleSignInHelper
import com.dev.chequpitest.data.local.dao.UserDao
import com.dev.chequpitest.data.mapper.toDomain
import com.dev.chequpitest.data.mapper.toEntity
import com.dev.chequpitest.domain.model.User
import com.dev.chequpitest.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInHelper: GoogleSignInHelper
) : AuthRepository {

    override suspend fun signInWithGoogle(): Result<User> {
        return try {
            val firebaseUserResult = googleSignInHelper.signInWithGoogle()
            firebaseUserResult.fold(
                onSuccess = { firebaseUser ->
                    val user = User(
                        id = firebaseUser.uid,
                        name = firebaseUser.displayName ?: "",
                        email = firebaseUser.email ?: "",
                        profileImageUrl = firebaseUser.photoUrl?.toString(),
                        isSignedIn = true
                    )
                    saveUser(user)
                    Result.success(user)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            clearUser()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser().map { userEntity ->
            userEntity?.toDomain()
        }
    }

    override suspend fun saveUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun clearUser() {
        userDao.deleteAllUsers()
    }
}
