package de.squiray.shutup.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.squiray.shutup.R
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

        shutUpWifi.setOnCheckedChangeListener { _, _ ->
            sharedPreferencesHandler!!.revertShutUpWifi()
            updateWifiConnectivity()
        }
        shutUpBluetooth.setOnCheckedChangeListener { _, _ ->
            sharedPreferencesHandler!!.revertShutUpBluetooth()
            updateBluetoothConnectivity()
        }
    }

    private fun updateWifiConnectivity() {
        shutUpWifi.isChecked = sharedPreferencesHandler!!.shutUpWifi()
        if (sharedPreferencesHandler!!.shutUpWifi()) {
            shutUpWifiText.setText(R.string.shut_up_wifi_on_screen_lock)
        } else {
            shutUpWifiText.setText(R.string.ignore_shut_up_wifi_on_screen_lock)
        }
    }

    private fun updateBluetoothConnectivity() {
        shutUpBluetooth.isChecked = sharedPreferencesHandler!!.shutUpBluetooth()
        if (sharedPreferencesHandler!!.shutUpBluetooth()) {
            shutUpBluetoothText.setText(R.string.shut_up_bluetooth_on_screen_lock)
        } else {
            shutUpBluetoothText.setText(R.string.ignore_shut_up_bluetooth_on_screen_lock)
        }
    }

}
