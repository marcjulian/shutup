package de.mrcjln.shutup.domain.model

import android.content.Context
import android.net.wifi.WifiManager
import timber.log.Timber

class WifiConnectivity(context: Context) : Connectivity {

    private val wifiManager: WifiManager
            = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun turnOn() {
        Timber.tag("WifiConnectivity").d("turn on")
        wifiManager.isWifiEnabled = true
    }

    override fun turnOff() {
        Timber.tag("WifiConnectivity").d("turn off")
        wifiManager.isWifiEnabled = false
    }

    override fun isOn(): Boolean = wifiManager.isWifiEnabled
}
