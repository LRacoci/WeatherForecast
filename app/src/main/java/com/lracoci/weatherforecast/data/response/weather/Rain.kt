package com.lracoci.weatherforecast.data.response.weather

import com.google.gson.annotations.SerializedName

data class Rain(
        @SerializedName("1h")
        val volume1h : Double = 0.0, //mm
        @SerializedName("3h")
        val volume3h : Double = 0.0//mm
)