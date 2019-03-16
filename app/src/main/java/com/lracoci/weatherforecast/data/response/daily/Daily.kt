package com.lracoci.weatherforecast.data.response.daily

import com.lracoci.weatherforecast.data.response.weather.Weather

data class Daily(
        val clouds: Double,
        val deg: Double,
        val dt: Long,
        val humidity: Double,
        val pressure: Double,
        val rain: Double,
        val snow: Double,
        val speed: Double,
        val temp: Temp,
        val weather: List<Weather>
)