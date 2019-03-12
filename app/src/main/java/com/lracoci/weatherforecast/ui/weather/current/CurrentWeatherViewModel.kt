package com.lracoci.weatherforecast.ui.weather.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lracoci.weatherforecast.data.repository.Repository

class CurrentWeatherViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = Repository(app.applicationContext)
    val weather = repository.weather
}
