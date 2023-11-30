package no.kristiania.productsApp.data;

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{productId}")
    suspend fun getProductById(
        @Path("productId") id: Int
    ): Response<Product>

}

