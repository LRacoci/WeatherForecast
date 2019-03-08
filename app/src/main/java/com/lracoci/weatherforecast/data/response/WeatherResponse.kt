package com.lracoci.weatherforecast.data.response

import com.lracoci.weatherforecast.data.response.weather.Clouds
import com.lracoci.weatherforecast.data.response.weather.Coord
import com.lracoci.weatherforecast.data.response.weather.Sys
import com.lracoci.weatherforecast.data.response.weather.Weather


data class WeatherResponse(
        val coord: Coord = Coord(),
        val weather: List<Weather> = listOf(Weather()),
        val base: String = "base",
        val main: Main = Main(),
        val wind: Wind = Wind(),
        val clouds: Clouds = Clouds(),
        val dt: Int = 0,
        val sys: Sys = Sys(),
        val id: Int = 0,
        val name: String = "name",
        val cod: Int = 0
)

// {
//    "coord": {
//        "lon": 139.01,
//        "lat": 35.02
//    },
//    "weather": [
//        {
//            "id": 800,
//            "main": "Clear",
//            "description": "clear sky",
//            "icon": "01n"
//        }
//    ],
//    "base": "stations",
//    "main": {
//        "temp": 285.514,
//        "pressure": 1013.75,
//        "humidity": 100,
//        "temp_min": 285.514,
//        "temp_max": 285.514,
//        "sea_level": 1023.22,
//        "grnd_level": 1013.75
//    },
//    "wind": {
//        "speed": 5.52,
//        "deg": 311
//    },
//    "clouds": {
//        "all": 0
//    },
//    "dt": 1485792967,
//    "sys": {
//        "message": 0.0025,
//        "country": "JP",
//        "sunrise": 1485726240,
//        "sunset": 1485763863
//    },
//    "id": 1907296,
//    "name": "Tawarano",
//    "cod": 200
//}