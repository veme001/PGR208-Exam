package no.kristiania.productsApp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import no.kristiania.productsApp.data.OrderItem

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(orderItem : OrderItem)

    @Query("SELECT * FROM Orders")
    suspend fun getAllOrders(): List<OrderItem>

}