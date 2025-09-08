package com.dev.chequpitest.di

import android.content.Context
import androidx.room.Room
import com.dev.chequpitest.data.auth.GoogleSignInHelper
import com.dev.chequpitest.data.local.dao.UserDao
import com.dev.chequpitest.data.local.database.AppDatabase
import com.dev.chequpitest.data.repository.AuthRepositoryImpl
import com.dev.chequpitest.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInHelper(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth
    ): GoogleSignInHelper {
        return GoogleSignInHelper(context, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        userDao: UserDao,
        firebaseAuth: FirebaseAuth,
        googleSignInHelper: GoogleSignInHelper
    ): AuthRepository {
        return AuthRepositoryImpl(context, userDao, firebaseAuth, googleSignInHelper)
    }
}
