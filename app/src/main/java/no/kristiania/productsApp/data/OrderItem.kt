package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Orders")
data class OrderItem (
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val products: List<Int>,
    val priceOfOrder: Double,
    val date: Long,
)
