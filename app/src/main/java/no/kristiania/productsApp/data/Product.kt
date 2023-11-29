package no.kristiania.products.data

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: Any,

)
data class ProductResponseList(val results: List<Product>)