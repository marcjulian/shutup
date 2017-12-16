package de.squiray.shutup.domain.model

import android.content.Context
import android.location.LocationManager
import timber.log.Timber
import android.content.Intent



class GpsConnectivity(val context: Context) : Connectivity {

    private val locationManager: LocationManager
            = context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override fun turnOn() {
        Timber.tag("GpsConnectivity").d("Gps is on ${isOn()}")
    }

    override fun turnOff() {
        Timber.tag("GpsConnectivity").d("Gps is on ${isOn()}")
    }

    override fun isOn(): Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
