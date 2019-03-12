package com.lracoci.weatherforecast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lracoci.weatherforecast.data.database.converters.WeatherListConverter
import com.lracoci.weatherforecast.data.database.daos.ForecastDao
import com.lracoci.weatherforecast.data.database.daos.WeatherDao
import com.lracoci.weatherforecast.data.response.ForecastResponse
import com.lracoci.weatherforecast.data.response.WeatherResponse

@Database(
        entities = [WeatherResponse::class, ForecastResponse::class],
        version = 1
)
@TypeConverters(WeatherListConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "futureWeatherEntries.db")
                        .build()

    }
}