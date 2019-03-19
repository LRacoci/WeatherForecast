package com.lracoci.weatherforecast.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lracoci.weatherforecast.data.response.WeatherResponse

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherResponse)
    @Query("select * from weather_response order by dt desc limit 1")
    fun getLast(): WeatherResponse?
    @Query("select * from weather_response order by dt desc limit 1")
    fun getWeather(): LiveData<WeatherResponse>
}
