package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Orders")
data class OrderItem (
    @PrimaryKey
    val orderId: Int,
    val products: List<Int>,
    val priceOfOrder: Double,
    val date: Long,
)
