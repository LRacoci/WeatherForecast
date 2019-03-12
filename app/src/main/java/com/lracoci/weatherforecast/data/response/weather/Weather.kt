package com.lracoci.weatherforecast.data.response.weather

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "weathers")
data class Weather(
        val id: Long = 0,
        val main: String = "main",
        val description: String = "description",
        val icon: String = "icon"
)