package com.lracoci.weatherforecast.data.response.forecast

import com.lracoci.weatherforecast.data.response.weather.Coord

data class City(
        val id: Int = 0,
        val name: String = "name",
        val coord: Coord = Coord(),
        val country: String = "country"
)