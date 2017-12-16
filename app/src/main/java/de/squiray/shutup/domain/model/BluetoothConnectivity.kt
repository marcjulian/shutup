package de.squiray.shutup.domain.model

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import de.squiray.shutup.util.SharedPreferencesHandler
import timber.log.Timber

class BluetoothConnectivity(context: Context) : Connectivity {

    private val bluetoothAdapter: BluetoothAdapter
            = (context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private val sharedPreferencesHandler: SharedPreferencesHandler
            = SharedPreferencesHandler(context)

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
