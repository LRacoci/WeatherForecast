package com.lracoci.weatherforecast.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lracoci.weatherforecast.data.response.forecast.Forecast
import org.threeten.bp.LocalDate

@Dao
interface ForecastDao {
    @Query("select count(forecastId) from forecasts where dt >= :now")
    fun countFutureWeather(now: Long): Int
    @Query("delete from forecasts where dt < :firstInstantToKeep")
    fun deleteOldEntries(firstInstantToKeep: Long)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherList: List<Forecast>)
    @Query("select * from forecasts where dt = :instant")
    fun getForecastByDate(instant: Long) : LiveData<Forecast>

}