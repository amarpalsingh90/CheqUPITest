package com.dev.chequpitest.di

import android.content.Context
import androidx.room.Room
import com.dev.chequpitest.constant.API_URL
import com.dev.chequpitest.data.auth.GoogleSignInHelper
import com.dev.chequpitest.data.local.dao.CartDao
import com.dev.chequpitest.data.local.dao.OrderDao
import com.dev.chequpitest.data.local.dao.UserDao
import com.dev.chequpitest.data.local.database.AppDatabase
import com.dev.chequpitest.data.remote.api.ProductApiService
import com.dev.chequpitest.data.repository.AuthRepositoryImpl
import com.dev.chequpitest.data.repository.CartRepositoryImpl
import com.dev.chequpitest.data.repository.OrderRepositoryImpl
import com.dev.chequpitest.data.repository.ProductRepositoryImpl
import com.dev.chequpitest.domain.repository.AuthRepository
import com.dev.chequpitest.domain.repository.CartRepository
import com.dev.chequpitest.domain.repository.OrderRepository
import com.dev.chequpitest.domain.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        ).fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
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
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productApiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(productApiService)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao
    ): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderDao: OrderDao
    ): OrderRepository {
        return OrderRepositoryImpl(orderDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        userDao: UserDao,
        firebaseAuth: FirebaseAuth,
        googleSignInHelper: GoogleSignInHelper
    ): AuthRepository {
        return AuthRepositoryImpl(userDao, firebaseAuth, googleSignInHelper)
    }
}
