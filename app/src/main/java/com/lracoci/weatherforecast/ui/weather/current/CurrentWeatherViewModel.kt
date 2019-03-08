package com.lracoci.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.lracoci.weatherforecast.data.OpenWeatherApiService
import com.lracoci.weatherforecast.data.response.WeatherResponse
import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

class CurrentWeatherViewModel : ViewModel() {
    val weather by lazyDeferred {
        val apiService = OpenWeatherApiService()
        apiService.getCurrentWeather()
        //forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}
