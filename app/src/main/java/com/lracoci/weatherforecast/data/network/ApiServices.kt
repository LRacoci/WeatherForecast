package com.resocoder.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lracoci.weatherforecast.data.network.NoConnectivityException
import com.lracoci.weatherforecast.data.network.OpenWeatherApi
import com.lracoci.weatherforecast.data.response.ForecastResponse
import com.lracoci.weatherforecast.data.response.WeatherResponse
import com.lracoci.weatherforecast.data.response.weather.GeoLocation

const val FORECAST_DAYS_COUNT = 7

class ApiServices(
        private val openWeather: OpenWeatherApi
) {

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse>
        get() = _weather

    suspend fun fetchWeather(loc : GeoLocation) {
        val fetchedCurrentWeather = openWeather
                .getWeather(loc.lat, loc.lon)
                .await()

        _weather.postValue(fetchedCurrentWeather)
    }

    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse>
        get() = _forecast

    suspend fun fetchForecast(loc : GeoLocation) {
        try {
            val fetchedFutureWeather = openWeather
                    .getForecast(loc.lat, loc.lon)
                    .await()
            _forecast.postValue(fetchedFutureWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

}