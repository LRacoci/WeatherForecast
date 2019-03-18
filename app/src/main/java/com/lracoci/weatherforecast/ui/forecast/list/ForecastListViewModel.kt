package com.lracoci.weatherforecast.ui.forecast.list

import android.app.Application
import com.lracoci.weatherforecast.data.repository.Repository
import com.lracoci.weatherforecast.ui.coroutines.ScopedAndroidViewModel
import com.lracoci.weatherforecast.ui.weather.lazyDeferred
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

class ForecastListViewModel(app: Application) : ScopedAndroidViewModel(app) {
    private val repository = Repository(app.applicationContext)
    val weatherEntries by lazyDeferred {
        val now = Instant.now().epochSecond
        repository.getForecast(now)
    }
}
