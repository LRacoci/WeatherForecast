package com.lracoci.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.lracoci.weatherforecast.R
import com.lracoci.weatherforecast.data.OpenWeatherApiService
import com.lracoci.weatherforecast.data.response.WeatherResponse
import com.lracoci.weatherforecast.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : ScopedFragment() {

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        bindUI()
        // TODO: Use the ViewModel
        val apiService = OpenWeatherApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse : WeatherResponse = apiService.getCurrentWeather().await()
            textView.text = currentWeatherResponse.toString()
        }
    }

    private fun bindUI() = launch {
        val weather = viewModel.weather.await()
        textView.text = weather.toString()
        /*weather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
            textView.text = weather.toString()
        })*/
    }

}
