package com.lracoci.weatherforecast.data.response

import com.lracoci.weatherforecast.data.response.daily.City
import com.lracoci.weatherforecast.data.response.daily.Daily

data class DailyResponse(
        val city: City,
        val cnt: Int,
        val cod: String,
        val list: List<Daily>,
        val message: Int
)