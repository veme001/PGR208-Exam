package no.kristiania.productsApp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.productsApp.data.ShoppingCartItem

// Data access object for interacting with the data in the Shopping Cart table
// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface ShoppingCartItemDao {

    //Asynchronous queries: https://developer.android.com/training/data-storage/room/async-queries
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