package com.lracoci.weatherforecast.data.response.weather

data class GeoLocation(
        val lon: Double = .0,
        val lat: Double = .0
)

fun approximatelyEqual(a : GeoLocation, b : GeoLocation, centralAngle: Double = 0.1) : Boolean {
    return  Math.sin(a.lat) * Math.sin(b.lat) +
            Math.cos(a.lat) * Math.cos(a.lat) * Math.cos(b.lon - a.lat) < Math.cos(centralAngle)
}