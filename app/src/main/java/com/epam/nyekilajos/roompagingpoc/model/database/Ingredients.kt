package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.ColumnInfo
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.reflect.KClass

@TypeConverters(StringListConverter::class)
data class Ingredients(

        @ColumnInfo(name = "malt")
        val malt: List<String>,

        @ColumnInfo(name = "hops")
        val hops: List<String>,

        @ColumnInfo(name = "yeast")
        val yeast: String


) {
    override fun toString(): String = "${malt.joinToString(", ")}, ${hops.joinToString(", ")}, $yeast"

}

abstract class ListConverter<T>(private val kClass: KClass<out List<T>>) {

    private val gson: Gson = GsonBuilder().create()

    @TypeConverter
    fun toList(jsonString: String): List<T> = gson.fromJson(jsonString, kClass.java)

    @TypeConverter
    fun toJson(list: List<T>): String = gson.toJson(list)
}

class StringListConverter : ListConverter<String>(mutableListOf<String>()::class)
