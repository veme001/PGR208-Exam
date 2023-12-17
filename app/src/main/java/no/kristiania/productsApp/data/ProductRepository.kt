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

    // Defining the http client (OkHTTP)
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    // An instance of retrofit for the api requests
    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // Creating a point of communication using the Service
    private val _productService =
        _retrofit.create(ProductService::class.java)

    // lateinit Instance of the database
    private lateinit var _appDatabase: AppDatabase

    // Function for initiating the Room database with the application context
    fun initiateDatabase(context: Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "appDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    // Async function that contacts the API "Products" endpoint. The function returns a list of products.
    // If the response is unsuccessful, it will get the products from the database.
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

    //Using the dao method for getting a product by its id, returns null if unsuccessful
    suspend fun getProductById(productId: Int): Product? {
        return try {
            _appDatabase.getProductDao().getProductById(productId)
        } catch (e: Exception) {
            Log.e("getProductById", "get product by id did not work", e)
            null
        }
    }

    //Using the dao method for getting several products by a list of ids, returns an empty list if unsuccessful
    suspend fun getProductsById(ids: List<Int>) : List<Product>{
        return try {
            _appDatabase.getProductDao().getProductsByIds(ids)
        } catch (e: Exception) {
            Log.e("getProductsById", "get products by ids did not work", e)
            emptyList()
        }

    }


    // Categories

    // Using the dao to get a list of products based on a specific category, passed as a parameter
    suspend fun getProductsByCategory(category: String): List<Product> {
        return try {
            val products = _appDatabase.getProductDao().getProductsByCategory(category)
            Log.d("getProductsByCategory", "Products for $category: $products")
            return products
        } catch (e: Exception) {
            Log.e("getProductsByCategory", "Failed to get products by category", e)
            emptyList()
        }
    }



    // Shopping Cart:

    // Using the Shopping cart dao to get all items in the shopping cart table. If the response is empty or if there
    // is an error, it will return an empty list.
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

    // Using the Shopping cart dao to add an item, it will check if the item (passed as an parameter) is existing.
    // If the item exists, the quantity will be updated by 1. If not, the item will be added to the table
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

    // Using the Shopping cart dao to edit the quantity ( edited in the shopping cart screen)
    // The function gets an item to update and a quantity to set as params
    suspend fun updateQuantity(productId: Int, quantity: Int) {
        try {
            _appDatabase.getShoppingCartDao().updateQuantity(productId, quantity)
        } catch (e: Exception){
            Log.e("updateQuantity", "Failed to update the quantity", e)
        }
    }

    // Using the Shopping cart dao to delete an item, the function gets the item from the table and
    // delete it if the item exists.
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

    // Using the Order dao to get all items in the Orders table. Returns an empty list on error
    suspend fun getOrders(): List<OrderItem>{
        return try {
            _appDatabase.getOrderDao().getAllOrders()
        } catch (e: Exception){
            Log.e("getOrders","Failed to get the orders", e)
            emptyList()
        }
    }

    // Using the Order dao to add an order, the order is passed as a parameter.
    suspend fun addOrder(order: OrderItem) {
        try {
            return _appDatabase.getOrderDao().insertOrder(order)
        } catch (e: Exception){
            Log.e("addOrders","Failed to add the order", e)
        }
    }

    // Using the Order dao to get an order by its id, the id and is passed as a parameter.
    // The function returns null if there is an error.
    suspend fun getOrderById(orderId: Int): OrderItem? {
        return try {
            _appDatabase.getOrderDao().getOrderById(orderId)
        } catch (e: Exception){
            Log.e("getOrderById","Failed to get the order by id", e)
            null
        }
    }
}
