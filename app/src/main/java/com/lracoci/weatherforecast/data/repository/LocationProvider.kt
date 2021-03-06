package com.lracoci.weatherforecast.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.lracoci.weatherforecast.data.response.WeatherResponse
import com.lracoci.weatherforecast.data.response.weather.GeoLocation
import com.lracoci.weatherforecast.data.response.weather.approximatelyEqual
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationPermissionNotGrantedException: Exception()
class InvalidLocationProvided : Exception()

class LocationProvider(
        context: Context
) {
    private val appContext = context.applicationContext
    private val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherResponse): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            true
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    suspend fun getPreferredLocation(): GeoLocation{
        if (isUsingDeviceLocation()) {
            while (true) {
                try {
                    getLastDeviceLocationAsync().await()?.let {
                        return GeoLocation(it.latitude,it.longitude)
                    }
                } catch (e: LocationPermissionNotGrantedException) {
                    continue
                }
            }
        }
        else
            return getCustomLocation()
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherResponse: WeatherResponse): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocationAsync().await()
                ?: return false

        return approximatelyEqual(
                lastWeatherResponse.geoLocaton,
                deviceLocation.run {
                    GeoLocation(latitude,longitude)
                },
                0.1
        )
    }

    private fun hasCustomLocationChanged(lastWeatherResponse: WeatherResponse): Boolean {
        if (isUsingDeviceLocation()) {
            return false
        }

        val deviceLocation = getCustomLocation()


        return approximatelyEqual(
                lastWeatherResponse.geoLocaton,
                deviceLocation,
                0.1
        )
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocation(): GeoLocation {
        val lat = preferences.getString("LATITUDE", "45.0")?.toDoubleOrNull()
        val lon = preferences.getString("LONGITUDE", "45.0")?.toDoubleOrNull()
        if (lat == null || lon == null) {
            throw Exception("Invalid Latitude and Longitude provided")
        }
        return GeoLocation(lat, lon)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocationAsync(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else {
            throw LocationPermissionNotGrantedException()
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}