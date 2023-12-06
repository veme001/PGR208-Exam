package no.kristiania.productsApp.data

import androidx.room.Entity

@Entity("ShoppingCart")
data class ShoppingCartItem(
    val productId: Int,
    val quantity: Int
)