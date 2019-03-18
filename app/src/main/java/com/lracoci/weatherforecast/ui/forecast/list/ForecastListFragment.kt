package com.lracoci.weatherforecast.ui.forecast.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer


import com.lracoci.weatherforecast.R

import com.lracoci.weatherforecast.ui.coroutines.ScopedFragment
import com.xwray.groupie.GroupAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import androidx.recyclerview.widget.LinearLayoutManager
import com.lracoci.weatherforecast.data.response.forecast.Forecast
import kotlinx.android.synthetic.main.future_list_weather_fragment.*


class ForecastListFragment  : ScopedFragment(){

    companion object {
        fun newInstance() =
            ForecastListFragment()
    }

    private lateinit var viewModel: ForecastListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val observableForecasts = viewModel.weatherEntries.await()

        observableForecasts.observe(this@ForecastListFragment, Observer { forecasts ->
            if (forecasts == null) return@Observer

            loadingForecast.visibility = View.GONE

            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
            //initRecyclerView(forecasts)
            forecastView.visibility = View.VISIBLE
            forecastView.text = forecasts.toString()
        })
    }

    /*private fun initRecyclerView(items: List<Forecast>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items.map{ForecastItem(it)})
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ForecastListFragment.context)
            adapter = groupAdapter
        }
        // Add Detail
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? Forecast)?.let {
                showWeatherDetail(it.dt, view)
            }
        }
    }
    private fun showWeatherDetail(dt : Long, view: View) {
        val actionDetail = ForecastListFragmentDirections.actionDetail(dt)
        Navigation.findNavController(view).navigate(actionDetail)
    }*/

}
