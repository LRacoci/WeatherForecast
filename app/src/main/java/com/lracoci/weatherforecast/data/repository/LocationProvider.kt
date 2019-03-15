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

open class LocationException(message: String) : Exception(message)
class LocationPermissionNotGrantedException: LocationException("Location Permission not Provided")
class LocationNotProvidedInPreferences: LocationException("Couldn't get the location from Preferences")

class LocationProvider(
        context: Context
) {
    private val appContext = context.applicationContext
    private val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherResponse): Boolean {
        return try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            hasCustomLocationChanged(lastWeatherLocation)
        }
    }

    suspend fun getLocation(): GeoLocation{
        return if (!isUsingDeviceLocation())
            getCustomLocation()
        else {
            try {
                val deviceLocation = getLastDeviceLocationAsync().await()
                GeoLocation(deviceLocation.latitude,deviceLocation.longitude)
            } catch (e: LocationPermissionNotGrantedException) {
                throw LocationException("Permission not provided and no Custom Location " +
                        "available on settings")
            }
        }

    }

    private suspend fun hasDeviceLocationChanged(lastWeatherResponse: WeatherResponse): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocationAsync().await()

        // Comparing doubles cannot be done with "=="
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
        return PreferenceManager.getDefaultSharedPreferences(appContext).getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocation(): GeoLocation {
        val lat = PreferenceManager.getDefaultSharedPreferences(appContext).getString("lat", "0.0")?.toDoubleOrNull()
        val lon = PreferenceManager.getDefaultSharedPreferences(appContext).getString("lon", "0.0")?.toDoubleOrNull()
        if (lat == null || lon == null) {
            throw LocationNotProvidedInPreferences()
        }
        return GeoLocation(lat, lon)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocationAsync(): Deferred<Location> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}