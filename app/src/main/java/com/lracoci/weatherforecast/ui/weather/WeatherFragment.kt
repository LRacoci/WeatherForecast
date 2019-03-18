package com.lracoci.weatherforecast.ui.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.lracoci.weatherforecast.R
import com.lracoci.weatherforecast.ui.coroutines.ScopedFragment
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class WeatherFragment : ScopedFragment() {
    companion object {
        fun newInstance() = WeatherFragment()
    }
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {

        /*(weatherRefresh as SwipeRefreshLayout).setOnRefreshListener {
            viewModel.update()
        }*/
        viewModel.weather.await().observe(this@WeatherFragment, Observer {
            if (it == null) return@Observer

            loading.visibility = View.GONE
            weatherView.visibility = View.VISIBLE
            val date = ZonedDateTime.ofInstant(Instant.ofEpochSecond(it.dt), ZoneId.of("UTC"))
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = date.toString()
            weatherView.text = it.toString()
        })
    }
}
