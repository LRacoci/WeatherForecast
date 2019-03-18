package com.lracoci.weatherforecast.ui.forecast.detail

import android.app.Application
import com.lracoci.weatherforecast.data.repository.Repository
import com.lracoci.weatherforecast.ui.coroutines.ScopedAndroidViewModel
import com.lracoci.weatherforecast.ui.weather.lazyDeferred

class FutureDetailWeatherViewModel(dt : Long, app: Application) : ScopedAndroidViewModel(app) {
    private val repository = Repository(app.applicationContext)
    val weather by lazyDeferred {
        repository.getForecastByDate(dt)
    }

}
