package de.mrcjln.shutup.domain.model

import android.content.Context
import android.net.wifi.WifiManager
import org.cryptomator.util.SharedPreferencesHandler
import timber.log.Timber

class WifiConnectivity(context: Context) : Connectivity {

    private val wifiManager: WifiManager
            = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val sharedPreferenceHandler: SharedPreferencesHandler
            = SharedPreferencesHandler(context)

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
