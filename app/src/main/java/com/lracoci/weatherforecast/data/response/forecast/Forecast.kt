package com.lracoci.weatherforecast.data.response.forecast

import com.google.gson.annotations.SerializedName
import com.lracoci.weatherforecast.data.response.weather.Clouds
import com.lracoci.weatherforecast.data.response.Main
import com.lracoci.weatherforecast.data.response.weather.Sys
import com.lracoci.weatherforecast.data.response.Wind
import com.lracoci.weatherforecast.data.response.weather.Weather

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
)