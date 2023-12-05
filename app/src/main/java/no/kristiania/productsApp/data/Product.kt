package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,

)
data class ProductResponseList(
    val results: List<Product>
)

data class Rating(
    val rate: Double,
    val count: Int
)