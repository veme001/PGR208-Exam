package no.kristiania.productsApp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromObject(value: String): Rating {
        val type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromRating(rating : Rating) : String {
        return gson.toJson(rating)
    }
}