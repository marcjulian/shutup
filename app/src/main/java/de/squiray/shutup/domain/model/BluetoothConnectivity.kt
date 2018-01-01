package de.squiray.shutup.domain.model

import android.bluetooth.BluetoothAdapter
import de.squiray.shutup.util.SharedPreferencesHandler
import timber.log.Timber
import javax.inject.Inject

class BluetoothConnectivity @Inject constructor(
        private val bluetoothAdapter: BluetoothAdapter,
        private val sharedPreferencesHandler: SharedPreferencesHandler
) : Connectivity {

    override fun turnOn() {
        if (sharedPreferencesHandler.shutUpBluetooth() && sharedPreferencesHandler.wasBluetoothOn()) {
            Timber.tag("BluetoothConnectivity").d("turn on")
            bluetoothAdapter.enable()
        }
    }

    override fun turnOff() {
        sharedPreferencesHandler.setBluetoothWasOn(isOn())
        if (sharedPreferencesHandler.shutUpBluetooth() && isOn()) {
            Timber.tag("BluetoothConnectivity").d("turn off")
            bluetoothAdapter.disable()
        }
    }

    override fun isOn(): Boolean = bluetoothAdapter.isEnabled
}
