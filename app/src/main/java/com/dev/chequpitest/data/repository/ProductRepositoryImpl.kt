package com.dev.chequpitest.data.repository

import com.dev.chequpitest.data.mapper.toDomain
import com.dev.chequpitest.data.remote.api.ProductApiService
import com.dev.chequpitest.domain.model.CheqUpiProduct
import com.dev.chequpitest.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<CheqUpiProduct> {
        return try {
            val response = productApiService.getProducts()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
