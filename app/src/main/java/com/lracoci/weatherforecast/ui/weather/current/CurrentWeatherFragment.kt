package com.lracoci.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lracoci.weatherforecast.R
import com.lracoci.weatherforecast.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
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
    }

    private fun bindUI() = launch {
        val weather = viewModel.weather.await()
        textView?.apply {
            text = weather.toString()
        }
        /*weather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
            textView.text = weather.toString()
        })*/
    }

}
