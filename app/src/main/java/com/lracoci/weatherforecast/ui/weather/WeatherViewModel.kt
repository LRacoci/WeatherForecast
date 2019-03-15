package com.lracoci.weatherforecast.ui.weather

import android.app.Application
import com.lracoci.weatherforecast.data.repository.Repository
import com.lracoci.weatherforecast.ui.coroutines.ScopedAndroidViewModel
import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

class WeatherViewModel(app: Application) : ScopedAndroidViewModel(app) {
    private val repository = Repository(app.applicationContext)

    val weather by lazyDeferred {
        repository.getWeather()
    }
}
