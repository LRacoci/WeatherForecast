package com.lracoci.weatherforecast.ui.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.lracoci.weatherforecast.R
import com.lracoci.weatherforecast.ui.coroutines.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch

class WeatherFragment : ScopedFragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.updateView()
        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.layout.observe(this@WeatherFragment, Observer {
            when(it.state){
                WeatherState.DONE -> {
                    weatherView.text = it.weather.toString()
                    weatherView.visibility = View.VISIBLE
                    loading.visibility = View.GONE
                }
                WeatherState.ERROR -> {
                    weatherView.text = it.errorMessage
                    weatherView.visibility = View.VISIBLE
                    loading.visibility = View.GONE
                }
                WeatherState.LOADING -> {
                    weatherView.text = "Loading ..."
                    weatherView.visibility = View.VISIBLE
                    loading.visibility = View.VISIBLE
                }
            }
        })

        /*weather.observe(this@WeatherFragment, Observer {
            if (it == null) return@Observer
            textView.text = weather.toString()
        })*/
    }

}
