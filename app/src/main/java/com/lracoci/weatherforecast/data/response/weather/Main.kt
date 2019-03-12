package com.lracoci.weatherforecast.data.response

import com.google.gson.annotations.SerializedName

data class Main(
        val pressure: Double = .0,
        val humidity: Int = 0,
        @SerializedName("sea_level")
        val seaLevel: Double = .0,
        @SerializedName("temp")
        val temperatureKelvin: Double = .0,
        @SerializedName("grnd_level")
        val groundLevel: Double = .0,
        @SerializedName("temp_min")
        val minTemperature: Double = .0,
        @SerializedName("temp_max")
        val maxTemperature: Double = .0,
        @SerializedName("temp_kf")
        val tempKf: Double = .0
)