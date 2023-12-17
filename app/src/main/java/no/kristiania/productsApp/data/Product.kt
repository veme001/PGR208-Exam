package no.kristiania.productsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Defining an entity in the database: https://developer.android.com/training/data-storage/room/defining-data
@Entity("Products")
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
// Object that is used to store the rating details (a list of values from the API)
data class Rating(
    val rate: Double,
    val count: Int
)