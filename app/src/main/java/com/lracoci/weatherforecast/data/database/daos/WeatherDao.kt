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
    fun update(weather: WeatherResponse)
    @Query("select * from weather_response")
    fun getLast(): WeatherResponse?
    //@Query("select a.* from weather_response a left outer join weather_response b on a.id = b.id and a.dt < b.dt where b.id = null")
    @Query("select * from weather_response where dt = ( select MAX(dt) from weather_response group by weatherId)")
    fun getWeather(): LiveData<WeatherResponse>
}
