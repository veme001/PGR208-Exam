package no.kristiania.productsApp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.kristiania.productsApp.data.Rating

//TypeConverters to help the Room database understand the values it will store
// https://developer.android.com/training/data-storage/room/referencing-data
object TypeConverter {
    private val gson = Gson()

    // Converting to and from the rating object (object that stores number of ratings and the score)
    @TypeConverter
    fun fromObject(value: String): Rating {
        val type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRating(rating : Rating) : String {
        return gson.toJson(rating)
    }

    //Converting to and from a list of ints (list of ids that is used in the order table)
    @TypeConverter
    fun fromIntList(value: List<Int>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }
}