package com.lracoci.weatherforecast.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lracoci.weatherforecast.data.response.ForecastResponse
import com.lracoci.weatherforecast.data.response.WeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val APPID = "1418c15b23265a66ae55318b98717418"

// https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=1418c15b23265a66ae55318b98717418

interface OpenWeatherApiService {

    @GET("weather")
    fun getWeather(
            @Query("lat") lat: Double = 35.0,
            @Query("lon") lon: Double = 139.0
    ): Deferred<WeatherResponse>

    @GET("forecast")
    fun getForecast(
            @Query("lat") lat: Double = 35.0,
            @Query("lon") lon: Double = 139.0
    ): Deferred<ForecastResponse>

    companion object {
        operator fun invoke(appContext: Context): OpenWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("appid", APPID)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }

            val connectivityInterceptor = Interceptor { chain ->
                //val appContext =  context.applicationContext
                val connectivityManager = appContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                val isOnline = networkInfo != null && networkInfo.isConnected
                if (!isOnline)
                    throw NoConnectivityException()
                chain.proceed(chain.request())
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(connectivityInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://samples.openweathermap.org/data/2.5/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OpenWeatherApiService::class.java)
        }
    }
}