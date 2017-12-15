package de.mrcjln.shutup.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.mrcjln.shutup.R
import kotlinx.android.synthetic.main.activity_shut_up.*
import org.cryptomator.util.SharedPreferencesHandler

class ShutUpActivity : AppCompatActivity() {

    private var sharedPreferencesHandler: SharedPreferencesHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shut_up)
        sharedPreferencesHandler = SharedPreferencesHandler(this)
        setupView()
    }

    private fun setupView() {
        updateWifiConnectivity()
        updateBluetoothConnectivity()

        wifiConnectivity.setOnClickListener {
            sharedPreferencesHandler!!.revertShutUpWifi()
            updateWifiConnectivity()
        }
        bluetoothConnectivity.setOnClickListener {
            sharedPreferencesHandler!!.revertShutUpBluetooth()
            updateBluetoothConnectivity()
        }
    }

    private fun updateWifiConnectivity() {
        if (sharedPreferencesHandler!!.shutUpWifi()) {
            wifiConnectivity.setImageResource(R.drawable.ic_wifi_off)
        } else {
            wifiConnectivity.setImageResource(R.drawable.ic_wifi)
        }
    }

    private fun updateBluetoothConnectivity() {
        if (sharedPreferencesHandler!!.shutUpBluetooth()) {
            bluetoothConnectivity.setImageResource(R.drawable.ic_bluetooth_disabled)
        } else {
            bluetoothConnectivity.setImageResource(R.drawable.ic_bluetooth)
        }
    }

}
