package com.lracoci.weatherforecast.ui.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lracoci.weatherforecast.data.repository.Repository
import com.lracoci.weatherforecast.data.response.WeatherResponse
import com.lracoci.weatherforecast.ui.coroutines.ScopedAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.properties.Delegates

class WeatherViewModel(val app: Application) : ScopedAndroidViewModel(app) {
    private val repository = Repository(app.applicationContext)

    private var _layout: MutableLiveData<WeatherLayoutModel> = MutableLiveData()
    var layout : LiveData<WeatherLayoutModel> = _layout

    init {
        _layout.value = WeatherLayoutModel()
    }

    fun updateView() = launch {
        val weatherRetrieved : LiveData<WeatherResponse>
        _layout.value?.apply {
            try {
                weatherRetrieved = repository.weather.await()
                weatherRetrieved.observeForever{
                    weather = it
                    state = WeatherState.DONE
                }
            } catch (e : Exception) {
                state = WeatherState.ERROR
                errorMessage = e.toString()
            }
        }

        _layout.postValue(_layout.value)
    }
}
