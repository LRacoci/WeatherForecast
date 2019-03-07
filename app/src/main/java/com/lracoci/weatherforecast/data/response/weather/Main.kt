package com.lracoci.weatherforecast.data.response

import com.google.gson.annotations.SerializedName

data class Main(
        val temp: Double,
        val pressure: Double,
        val humidity: Int,
        @SerializedName("temp_min")
        val tempMin: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("sea_level")
        val seaLevel: Double,
        @SerializedName("grnd_level")
        val grndLevel: Double
)