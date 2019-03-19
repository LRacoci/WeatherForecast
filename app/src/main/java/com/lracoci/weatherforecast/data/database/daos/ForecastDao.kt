package com.lracoci.weatherforecast.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lracoci.weatherforecast.data.response.ForecastResponse

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecast: ForecastResponse)
    @Query("select * from forecast_response order by id desc limit 1")
    fun getLast(): ForecastResponse?
    @Query("select * from forecast_response order by id desc limit 1")
    fun getForecast(): LiveData<ForecastResponse>
}
