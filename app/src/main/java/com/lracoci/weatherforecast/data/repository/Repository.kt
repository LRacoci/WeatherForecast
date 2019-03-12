package com.lracoci.weatherforecast.data.repository

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.lracoci.weatherforecast.data.database.AppDatabase
import com.lracoci.weatherforecast.data.network.NoConnectivityException
import com.lracoci.weatherforecast.data.network.OpenWeatherApiService
import com.lracoci.weatherforecast.data.response.ForecastResponse
import com.lracoci.weatherforecast.data.response.WeatherResponse
import com.resocoder.forecastmvvm.data.network.ApiServices
import com.resocoder.forecastmvvm.data.network.FORECAST_DAYS_COUNT
import kotlinx.coroutines.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener { result ->
        deferred.complete(result)
    }

    this.addOnFailureListener { exception ->
        deferred.completeExceptionally(exception)
    }

    return deferred
}

class Repository (
        val appContext : Context
){
    //var weather : LiveData<WeatherResponse> = null
    private val db = AppDatabase(appContext)
    private val weatherDao = db.weatherDao()
    private val forecastDao = db.forecastDao()
    private val openWeatherApiService = OpenWeatherApiService(appContext)
    private val apiService = ApiServices(openWeatherApiService)
    private val locationProvider = LocationProvider(appContext)

    val weather by lazyDeferred {
        getCurrentWeather()
    }

    init {
        // Watch downloaded data from apiService
        apiService.forecast.observeForever {
            persist(it)
        }
    }

    private suspend fun getCurrentWeather(): LiveData<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext weatherDao.getWeather()
        }
    }
    private suspend fun initWeatherData() {
        val lastWeather = weatherDao.getLast()

        if (lastWeather == null
                || locationProvider.hasLocationChanged(lastWeather)) {
            fetchWeather()
            //fetchFutureWeather()
            return
        }
        val instant = Instant.ofEpochSecond(lastWeather.utcUnixInstant)
        val zoneId = ZoneId.of("UTC")
        val zonedDataTime = ZonedDateTime.ofInstant(instant, zoneId)
        if (isFetchCurrentNeeded(zonedDataTime))
            fetchWeather()

        if (isFetchFutureNeeded())
            fetchForecast()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    @WorkerThread
    private suspend fun fetchWeather() {
        val geoLocation = locationProvider.getPreferredLocationString()
        try {
            val downloaded = openWeatherApiService.getWeather(geoLocation.lat, geoLocation.lon).await()
            persist(downloaded)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
    @WorkerThread
    private suspend fun fetchForecast() {
        val geoLocation = locationProvider.getPreferredLocationString()
        apiService.fetchForecast(geoLocation)
    }
    @WorkerThread
    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = forecastDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

    @WorkerThread
    private fun persist(weather : WeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.update(weather)
        }
    }

    @WorkerThread
    private fun persist(forecast : ForecastResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val today = LocalDate.now()
            forecastDao.deleteOldEntries(today)
            val futureWeatherList = forecast.list
            forecastDao.insert(futureWeatherList)
        }
    }

}