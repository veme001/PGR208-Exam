package no.kristiania.productsApp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}