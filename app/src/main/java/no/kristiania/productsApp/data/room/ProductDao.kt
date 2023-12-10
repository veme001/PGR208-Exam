package no.kristiania.productsApp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
<<<<<<< HEAD:app/src/main/java/no/kristiania/productsApp/data/room/ProductDao.kt
import no.kristiania.productsApp.data.Product
=======
import java.util.Locale.Category
>>>>>>> beb6d0059f0b697c974f9d5ffa3516eb0765bc7e:app/src/main/java/no/kristiania/productsApp/data/ProductDao.kt

@Dao
interface ProductDao {

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
