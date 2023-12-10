package no.kristiania.productsApp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import no.kristiania.productsApp.data.Product
import no.kristiania.productsApp.data.ShoppingCartItem

@Database(
    entities = [Product::class, ShoppingCartItem::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getShoppingCartDao() : ShoppingCartItemDao
}