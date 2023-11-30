package no.kristiania.productsApp.data;

import no.kristiania.products.data.Product
import no.kristiania.products.data.ProductResponseList;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path

interface ProductService {

    @GET("products/{productId}")
    suspend fun getProductById(
        @Path("productId") id: Int
    ): Response<Product>

}

