package com.lracoci.weatherforecast.data.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lracoci.weatherforecast.data.response.forecast.City
import com.lracoci.weatherforecast.data.response.forecast.Forecast

@Entity(tableName = "forecast_response")
data class ForecastResponse(
        @SerializedName("cod")
        @Ignore
        var code: String = "",
        @Ignore
        var message: Double = 0.0,
        @SerializedName("cnt")
        var count: Int = 0,
        @Ignore
        var list: List<Forecast> = listOf(Forecast()),
        @Embedded(prefix = "city_")
        var city: City = City(),
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
)