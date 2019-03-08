package com.lracoci.weatherforecast.data.repository

import com.lracoci.weatherforecast.data.OpenWeatherApiService
import com.lracoci.weatherforecast.data.response.WeatherResponse
import kotlinx.coroutines.Deferred

class Repository() {
    //var weather : LiveData<WeatherResponse> = null
    val weather : Deferred<WeatherResponse>
        get() {
            val apiService = OpenWeatherApiService()
            return apiService.getCurrentWeather()
        }
}