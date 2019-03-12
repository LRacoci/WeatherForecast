package com.lracoci.weatherforecast.data.response
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lracoci.weatherforecast.data.response.weather.*

//@TypeConverters(Weather.class)
@Entity(tableName = "weather_response")
data class WeatherResponse(
        @Embedded(prefix = "coord_")
        @SerializedName("coord")
        val geoLocaton: GeoLocation = GeoLocation(),
        val weather: List<Weather> = listOf(Weather()),
        val base: String = "base",
        @SerializedName("dt")
        val utcUnixInstant: Long = 0,
        @SerializedName("main")
        @Embedded(prefix = "main_")
        val main: Main = Main(),
        @Embedded(prefix = "wind_")
        val wind: Wind = Wind(),
        @Embedded(prefix = "clouds_")
        val clouds: Clouds = Clouds(),
        @Embedded(prefix = "rain_")
        val rain: Rain = Rain(),
        @Embedded(prefix = "snow_")
        val snow: Snow = Snow(),
        @Embedded(prefix = "sys_")
        val sys: Sys = Sys(),
        @SerializedName("id")
        val cityId: Int = 0,
        @SerializedName("name")
        val cityName: String = "name"
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

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
//    "utcUnixInstant": 1485792967,
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