package com.lracoci.weatherforecast.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lracoci.weatherforecast.data.response.weather.Weather

object WeatherListConverter {
    val gson = Gson()
    @TypeConverter
    @JvmStatic
    fun encode(list : List<Weather>) : String {
        return gson.toJson(list)
    }
    @TypeConverter
    @JvmStatic
    fun decode(json : String) : List<Weather> {
        return gson.fromJson(json, Array<Weather>::class.java).asList()
    }
}