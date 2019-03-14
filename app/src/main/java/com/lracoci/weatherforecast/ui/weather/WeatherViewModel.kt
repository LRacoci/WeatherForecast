package com.lracoci.weatherforecast.ui.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lracoci.weatherforecast.data.repository.Repository
import com.lracoci.weatherforecast.data.response.WeatherResponse
import com.lracoci.weatherforecast.ui.coroutines.ScopedAndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.properties.Delegates

class WeatherViewModel(app: Application) : ScopedAndroidViewModel(app) {
    private val repository = Repository(app.applicationContext)


    private var _layout: MutableLiveData<WeatherLayoutModel> = MutableLiveData()
    var layout : LiveData<WeatherLayoutModel> = _layout

//    private var _weather: MutableLiveData<WeatherResponse> = MutableLiveData()
//    val weather: LiveData<WeatherResponse> = _weather
//    //var weather: WeatherResponse = WeatherResponse()
//
//    private var _state: MutableLiveData<WeatherState> = MutableLiveData()
//    val state: LiveData<WeatherState> = _state
//    //var state: WeatherState = WeatherState.LOADING //LiveData<WeatherState> = _state
//
//    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
//    val errorMessage: LiveData<String> = _errorMessage
//    //var errorMessage: String? = null//: LiveData<String> = _errorMessage

    init {
        _layout.value = WeatherLayoutModel()
//        _weather.value = WeatherResponse()
//        _state.value = WeatherState.LOADING
    }

    fun updateView() = launch {
        var weatherRetrieved : WeatherResponse? = null

        _layout.value?.apply {
            try {
                weatherRetrieved = repository.weather.await()
            } catch (e : Exception) {
                state = WeatherState.ERROR
                errorMessage = e.message
            }
            weatherRetrieved?.let{
                weather = it
                state = WeatherState.DONE
            }
        }

        _layout.postValue(_layout.value)

//        try {
//            weatherRetrieved = repository.weather.await()
//        } catch (e : Exception) {
//            _state.postValue(WeatherState.ERROR)
//            //state = WeatherState.ERROR
//            _errorMessage.postValue(e.message)
//            //errorMessage = e.message
//        }
//        weatherRetrieved?.let{
//            _weather.postValue(it)
//            //weather = it
//            _state.postValue(WeatherState.DONE)
//            //state = WeatherState.DONE
//        }

    }
}
