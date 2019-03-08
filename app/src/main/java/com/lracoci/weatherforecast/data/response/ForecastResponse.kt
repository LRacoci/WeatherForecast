package com.lracoci.weatherforecast.data.response

import com.lracoci.weatherforecast.data.response.forecast.City
import com.lracoci.weatherforecast.data.response.forecast.Forecast

data class ForecastResponse(
        val cod: String,
        val message: Double,
        val cnt: Int,
        val list: List<Forecast>,
        val city: City
)