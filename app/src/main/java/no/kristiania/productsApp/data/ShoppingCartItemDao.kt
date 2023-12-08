package no.kristiania.productsApp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingCartItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingCartItem)

    @Query("SELECT * FROM ShoppingCart")
    suspend fun getShoppingCart(): List<ShoppingCartItem>

    @Query("UPDATE ShoppingCart SET quantity = :newQuantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, newQuantity: Int)

    @Delete
    suspend fun deleteItem(item: ShoppingCartItem)

    @Query("SELECT * FROM ShoppingCart WHERE :productId = productId")
    suspend fun getItemById(productId: Int): ShoppingCartItem?
}