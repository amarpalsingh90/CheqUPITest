package com.dev.chequpitest.domain.repository

import com.dev.chequpitest.domain.model.CheqUpiProduct
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Result<CheqUpiProduct>
}
