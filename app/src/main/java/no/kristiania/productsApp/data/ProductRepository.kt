package no.kristiania.productsApp.data

import no.kristiania.products.data.Product
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {

    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService =
        _retrofit.create(ProductService::class.java)

    suspend fun getProductById(productId: Int): Product? {
        val response =
            _productService.getProductById(productId)

        return if(response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

}
