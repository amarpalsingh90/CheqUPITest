package com.dev.chequpitest.data.remote.api

import com.dev.chequpitest.data.remote.dto.CheqUpiProductDto
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): CheqUpiProductDto
}
