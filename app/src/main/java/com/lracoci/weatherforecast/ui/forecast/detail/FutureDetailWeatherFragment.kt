package com.lracoci.weatherforecast.ui.forecast.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.lracoci.weatherforecast.R
import com.lracoci.weatherforecast.ui.coroutines.ScopedFragment
import kotlinx.android.synthetic.main.forecast_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FutureDetailWeatherFragment : ScopedFragment() {

    companion object {
        fun newInstance() =
            FutureDetailWeatherFragment()
    }

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FutureDetailWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.weather.await()


        futureWeather.observe(this@FutureDetailWeatherFragment, Observer { weatherEntry ->
            if (weatherEntry == null) return@Observer

            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Test"

            detailView.text = weatherEntry.toString()

        })
    }

}
