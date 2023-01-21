package com.app.examexchange.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class  MapConverter {
    companion object{
        @JvmStatic
        @TypeConverter
        fun fromString(value: String): Map<String, Int> {
            val mapType = object : TypeToken<Map<String, Int>>() {}.type
            return Gson().fromJson(value, mapType)
        }
        @TypeConverter
        @JvmStatic
        fun fromStringMap(map: Map<String, Int>): String {
            val gson = Gson()
            return gson.toJson(map)
        }
    }
}
