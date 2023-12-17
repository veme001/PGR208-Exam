package no.kristiania.productsApp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import no.kristiania.productsApp.data.OrderItem

// Data access object for interacting with the data in the Orders table
// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface OrderDao {

    //Asynchronous queries: https://developer.android.com/training/data-storage/room/async-queries
    @Insert
    suspend fun insertOrder(orderItem : OrderItem)

    @Query("SELECT * FROM Orders")
    suspend fun getAllOrders(): List<OrderItem>

    @Query("SELECT * FROM orders WHERE :orderId = orderId")
    suspend fun getOrderById(orderId: Int?): OrderItem?

}