package no.kristiania.productsApp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import no.kristiania.productsApp.data.room.AppDatabase
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
        return try {
            _appDatabase.getProductDao().getProductById(productId)
        } catch (e: Exception) {
            Log.e("getProductById", "get product by id did not work", e)
            null
        }
    }

    suspend fun getProductsById(ids: List<Int>) : List<Product>{
        return try {
            _appDatabase.getProductDao().getProductsByIds(ids)
        } catch (e: Exception) {
            Log.e("getProductsById", "get products by ids did not work", e)
            emptyList()
        }

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
        return try {
            val itemsList = _appDatabase.getShoppingCartDao().getShoppingCart()

            itemsList.ifEmpty {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("getShoppingCart", "Failed to get the shoppingCart from room", e)
            emptyList()
        }
    }

    suspend fun addShoppingCartItem(item: ShoppingCartItem) {
        try {
            val exists = _appDatabase.getShoppingCartDao().getItemById(item.productId)

            if (exists != null) {
                _appDatabase.getShoppingCartDao().updateQuantity(item.productId, exists.quantity + 1)
            } else {
                _appDatabase.getShoppingCartDao().insertItem(item)
            }
        } catch (e: Exception){
            Log.e("addShoppingCartItem", "Failed to add item to database", e)
        }
    }

    suspend fun updateQuantity(productId: Int, quantity: Int) {
        try {
            _appDatabase.getShoppingCartDao().updateQuantity(productId, quantity)
        } catch (e: Exception){
            Log.e("updateQuantity", "Failed to update the quantity", e)
        }

    }

    suspend fun removeShoppingCartItem(item: ShoppingCartItem) {
        try {
            val itemToDelete = _appDatabase.getShoppingCartDao().getItemById(item.productId)

            if (itemToDelete != null) {
                _appDatabase.getShoppingCartDao().deleteItem(item)
            }
        } catch (e: Exception){
            Log.e("removeShoppingCartItem", "Failed to delete the item from the shopping cart", e)
        }
    }


    // Orders:
    suspend fun getOrders(): List<OrderItem>{
        return try {
            _appDatabase.getOrderDao().getAllOrders()
        } catch (e: Exception){
            Log.e("getOrders","Failed to get the orders", e)
            emptyList()
        }
    }

    suspend fun addOrder(order: OrderItem) {
        try {
            return _appDatabase.getOrderDao().insertOrder(order)
        } catch (e: Exception){
            Log.e("addOrders","Failed to add the order", e)
        }
    }

    suspend fun getOrderById(orderId: Int): OrderItem? {
        return try {
            _appDatabase.getOrderDao().getOrderById(orderId)
        } catch (e: Exception){
            Log.e("getOrderById","Failed to get the order by id", e)
            null
        }
    }
}
