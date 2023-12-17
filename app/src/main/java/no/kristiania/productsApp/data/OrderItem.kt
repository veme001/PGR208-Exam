package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Defining an entity in the database: https://developer.android.com/training/data-storage/room/defining-data
@Entity("Orders")
data class OrderItem (
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val products: List<Int>,
    val priceOfOrder: Double,
    val date: Long,
)

// Object defining details of an order placed by the user
data class OrderDetails(
    val orderId: Int,
    val date: String,
    val numberOfItems: Int,
    val totalOrderPrice: Double,
    val products: List<ProductQuantity>
)

// Object defining the product in an order and its quantity
data class ProductQuantity(
    val productId: Int,
    val productName: String,
    val quantity: Int
)
