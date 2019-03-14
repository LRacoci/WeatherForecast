package com.lracoci.weatherforecast.ui.weather

import com.lracoci.weatherforecast.data.response.WeatherResponse

class WeatherLayoutModel(
    var weather : WeatherResponse = WeatherResponse(),
    var state: WeatherState = WeatherState.LOADING,
    var errorMessage: String? = "Error message"
) {

}