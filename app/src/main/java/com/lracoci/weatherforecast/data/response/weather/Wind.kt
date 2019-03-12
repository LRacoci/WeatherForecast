package com.lracoci.weatherforecast.data.response

import com.google.gson.annotations.SerializedName

data class Wind(
        val speed: Double = .0,
        @SerializedName("deg")
        val direction: Double = .0
)