package no.kristiania.productsApp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    suspend fun getAllProducts() : List<Product>

    @Insert
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM Product WHERE :id = id")
    suspend fun getProductById(id: Int?): Product?
}
