package no.kristiania.productsApp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    suspend fun getAllProducts() : List<Product>

    @Insert
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM Products WHERE :id = id")
    suspend fun getProductById(id: Int?): Product?

    @Query("SELECT * FROM Products WHERE id in (:ids)")
    suspend fun getProductsByIds(ids : List<Int>): List<Product>
}
