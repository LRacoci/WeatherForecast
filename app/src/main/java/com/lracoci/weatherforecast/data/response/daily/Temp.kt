package com.lracoci.weatherforecast.data.response.daily

data class Temp(
        val day: Double,
        val eve: Double,
        val max: Double,
        val min: Double,
        val morn: Double,
        val night: Double
)