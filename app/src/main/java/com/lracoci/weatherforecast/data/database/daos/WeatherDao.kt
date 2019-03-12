package com.lracoci.weatherforecast.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.lracoci.weatherforecast.data.response.WeatherResponse

interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(weather: WeatherResponse)

    fun getLast(): WeatherResponse?
    fun getWeather(): LiveData<WeatherResponse>
}
