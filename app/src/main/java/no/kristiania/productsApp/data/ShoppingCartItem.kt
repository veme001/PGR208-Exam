package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ShoppingCart")
data class ShoppingCartItem(
    @PrimaryKey
    val productId: Int,
    val quantity: Int
)