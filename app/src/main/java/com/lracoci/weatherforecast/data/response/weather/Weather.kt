package com.lracoci.weatherforecast.data.response.weather

import androidx.room.Entity

@Entity(tableName = "weathers")
data class Weather(
        val id: Int = 0,
        val main: String = "main",
        val description: String = "description",
        val icon: String = "icon"
)