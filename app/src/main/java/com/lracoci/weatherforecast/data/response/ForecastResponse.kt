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
        val code: String,
        val message: Double,
        @SerializedName("cnt")
        val count: Int,
        @Ignore
        val list: List<Forecast>,
        @Embedded(prefix = "city_")
        val city: City
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}