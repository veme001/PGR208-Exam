package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Defining an entity in the database: https://developer.android.com/training/data-storage/room/defining-data
@Entity("ShoppingCart")
data class ShoppingCartItem(
    @PrimaryKey
    val productId: Int,
    val quantity: Int
)