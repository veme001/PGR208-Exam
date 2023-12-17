package no.kristiania.productsApp.data;

import retrofit2.Response
import retrofit2.http.GET

// Service to interact with the api endpoints
interface ProductService {

    //The baseurl in the repository is used to get the "products" endpoint,
    // the async function returns a list of products
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

}

