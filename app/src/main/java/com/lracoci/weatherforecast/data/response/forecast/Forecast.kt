package com.lracoci.weatherforecast.data.response.forecast

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import com.lracoci.weatherforecast.data.response.ForecastResponse
import com.lracoci.weatherforecast.data.response.weather.Clouds
import com.lracoci.weatherforecast.data.response.Main
import com.lracoci.weatherforecast.data.response.weather.Sys
import com.lracoci.weatherforecast.data.response.Wind
import com.lracoci.weatherforecast.data.response.weather.Rain
import com.lracoci.weatherforecast.data.response.weather.Weather
//@Entity(tableName = "forecasts")
@Entity(tableName = "forecasts"/*,
    indices = [Index(value = ["resp_id"])],
    foreignKeys = [ForeignKey(entity = ForecastResponse::class,
        onDelete=CASCADE,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("resp_id")
    )]*/
)
data class Forecast(
        var dt: Long = 0,
        @Embedded(prefix = "main_")
        var main: Main = Main(),
        var weather: List<Weather> = listOf(Weather()),
        @Embedded(prefix = "clouds_")
        var clouds: Clouds = Clouds(),
        @Embedded(prefix = "wind_")
        var wind: Wind = Wind(),
        @Embedded(prefix = "rain_")
        var rain: Rain = Rain(),
        @Embedded(prefix = "sys_")
        var sys: Sys = Sys(),
        @SerializedName("dt_txt")
        var dtTxt: String = "dtTxt"
){
    @PrimaryKey(autoGenerate = true)
    var forecastId : Int = 0
    //var resp_id : Int = 0
}