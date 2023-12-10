package no.kristiania.productsApp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale.Category

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

    private lateinit var _appDatabase: AppDatabase

    fun initiateDatabase(context: Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "appDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    suspend fun getAllProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()

            if(response.isSuccessful){

                val products = response.body() ?: emptyList()
                _appDatabase.getProductDao().insertProducts(products)

                return _appDatabase.getProductDao().getAllProducts()
            } else {
                throw Exception("could not get all products")
            }
        } catch (e: Exception){
            Log.e("getProducts", "get products did not work", e)
            return _appDatabase.getProductDao().getAllProducts()
        }
    }

        suspend fun getProductById(productId: Int): Product? {
            return _appDatabase.getProductDao().getProductById(productId)
        }

        suspend fun getProductsById(ids: List<Int>) : List<Product>{
            return _appDatabase.getProductDao().getProductsByIds(ids)
        }

        // Categories

        suspend fun getProductsByCategory(category: String): List<Product> {
            try {
                val response = _productService.getProductsByCategory(category)

                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    _appDatabase.getProductDao().insertProducts(products)

                    Log.d("getProductsByCategory", "Products for $category: ${products}")
                    return _appDatabase.getProductDao().getProductsByCategory(category)
                } else {
                    Log.e("getProductsByCategory", "Unsuccessful response: ${response.code()}")
                    throw Exception("could not get products by category")
                }

            } catch (e: Exception) {
                Log.e("getProductsByCategory", "Failed to get products by category", e)
                return emptyList()
            }
        }


        // Shopping Cart:
        suspend fun getShoppingCart(): List<ShoppingCartItem> {
            val itemsList = _appDatabase.getShoppingCartDao().getShoppingCart()

            return itemsList.ifEmpty {
                emptyList()
            }
        }

        suspend fun addShoppingCartItem(item: ShoppingCartItem) {
            val exists = _appDatabase.getShoppingCartDao().getItemById(item.productId)

            if (exists != null) {
                _appDatabase.getShoppingCartDao().updateQuantity(item.productId, item.quantity + 1)
            } else {
                _appDatabase.getShoppingCartDao().insertItem(item)
            }
        }

        suspend fun updateQuantity(productId: Int, quantity: Int) {
            _appDatabase.getShoppingCartDao().updateQuantity(productId, quantity)
        }

        suspend fun removeShoppingCartItem(item: ShoppingCartItem) {
            val itemToDelete = _appDatabase.getShoppingCartDao().getItemById(item.productId)

            if (itemToDelete != null) {
                _appDatabase.getShoppingCartDao().deleteItem(item)
            }
        }
}
