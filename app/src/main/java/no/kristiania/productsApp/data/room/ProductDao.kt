package no.kristiania.productsApp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.productsApp.data.Product

// Data access object for interacting with the data in the Products table
// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface ProductDao {

    //Asynchronous queries: https://developer.android.com/training/data-storage/room/async-queries
    @Query("SELECT * FROM Products")
    suspend fun getAllProducts() : List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM Products WHERE :id = id")
    suspend fun getProductById(id: Int?): Product?

    @Query("SELECT * FROM Products WHERE id in (:ids)")
    suspend fun getProductsByIds(ids : List<Int>): List<Product>

    @Query("SELECT * FROM Products WHERE category = :category")
    suspend fun getProductsByCategory(category: String): List<Product>
}
