package com.lracoci.weatherforecast.data.response.weather

import com.google.gson.annotations.SerializedName

data class Clouds(
        @SerializedName("all")
        val cloudinessPercentage: Double = 0.0
)