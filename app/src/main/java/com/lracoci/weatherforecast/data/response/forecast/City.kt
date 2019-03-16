package com.lracoci.weatherforecast.data.response.forecast

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import com.lracoci.weatherforecast.data.response.weather.GeoLocation

data class City(
        val id: Int = 0,
        val name: String = "name",
        @Embedded(prefix = "coord_")
        @SerializedName("coord")
        val geoLocation: GeoLocation = GeoLocation(),
        val country: String = "country"
)