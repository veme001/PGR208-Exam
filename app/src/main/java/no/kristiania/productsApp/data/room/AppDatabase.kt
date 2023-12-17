package no.kristiania.productsApp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import no.kristiania.productsApp.data.OrderItem
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ShoppingCartItem

//Declaring the database and its entities
@Database(
    entities = [Product::class, ShoppingCartItem::class, OrderItem:: class],
    version = 1,
    exportSchema = false
)
// Declaring the TypeConverter class
@TypeConverters(TypeConverter::class)

// Declaring the datbase as an extension of Room
abstract class AppDatabase : RoomDatabase() {

    //methods to access the dao´s
    abstract fun getProductDao(): ProductDao
    abstract fun getShoppingCartDao() : ShoppingCartItemDao
    abstract fun getOrderDao() : OrderDao
}
