package com.lracoci.weatherforecast.data.database.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.lracoci.weatherforecast.data.response.forecast.Forecast
import org.threeten.bp.LocalDate

interface ForecastDao {

    fun countFutureWeather(today: LocalDate?): Int

    fun deleteOldEntries(today: LocalDate?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherList: List<Forecast>)

}