package com.lracoci.weatherforecast.ui.forecast.list

import com.lracoci.weatherforecast.R
import com.lracoci.weatherforecast.data.response.forecast.Forecast
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.forecast_item.view.*

class ForecastItem(
        private val forecastItem: Forecast
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.containerView.forecastItemView.text = forecastItem.toString()
    }

    override fun getLayout() = R.layout.forecast_item

}