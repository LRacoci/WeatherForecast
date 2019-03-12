package com.lracoci.weatherforecast.data.response.forecast

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lracoci.weatherforecast.data.response.ForecastResponse
import com.lracoci.weatherforecast.data.response.weather.Clouds
import com.lracoci.weatherforecast.data.response.Main
import com.lracoci.weatherforecast.data.response.weather.Sys
import com.lracoci.weatherforecast.data.response.Wind
import com.lracoci.weatherforecast.data.response.weather.Rain
import com.lracoci.weatherforecast.data.response.weather.Weather
//@Entity(tableName = "forecasts")
@Entity(tableName = "forecasts",
        foreignKeys = arrayOf(
                ForeignKey(entity = ForecastResponse::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("resp_id")
                )
        )
)
data class Forecast(
        val dt: Int = 0,
        val main: Main = Main(),
        val weather: List<Weather>,
        val clouds: Clouds = Clouds(),
        val wind: Wind = Wind(),
        val rain: Rain = Rain(),
        val sys: Sys = Sys(),
        @SerializedName("dt_txt")
        val dtTxt: String = "dtTxt"
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var resp_id : Int = 0
}