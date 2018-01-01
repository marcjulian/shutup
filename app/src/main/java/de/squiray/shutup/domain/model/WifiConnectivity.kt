package de.squiray.shutup.domain.model

import android.net.wifi.WifiManager
import de.squiray.shutup.util.SharedPreferencesHandler
import timber.log.Timber
import javax.inject.Inject

class WifiConnectivity @Inject constructor(
        private val wifiManager: WifiManager,
        private val sharedPreferenceHandler: SharedPreferencesHandler
) : Connectivity {

    override fun turnOn() {
        if (sharedPreferenceHandler.shutUpWifi() && sharedPreferenceHandler.wasWifiOn()) {
            Timber.tag("WifiConnectivity").d("turn on")
            wifiManager.isWifiEnabled = true
        }
    }

    override fun turnOff() {
        sharedPreferenceHandler.setWifiWasOn(isOn())
        if (sharedPreferenceHandler.shutUpWifi() && isOn()) {
            Timber.tag("WifiConnectivity").d("turn off")
            wifiManager.isWifiEnabled = false
        }
    }

    override fun isOn(): Boolean = wifiManager.isWifiEnabled
}
