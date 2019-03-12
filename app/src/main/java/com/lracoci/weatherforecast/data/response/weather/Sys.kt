package com.lracoci.weatherforecast.data.response.weather

import com.google.gson.annotations.SerializedName

data class Sys(
        @SerializedName("country")
        val countryCode: String = "country",
        @SerializedName("sunrise")
        val sunriseInstant: Long = 0,
        @SerializedName("sunset")
        val sunsetInstant: Long = 0,
        val pod: String = "pod"
)
