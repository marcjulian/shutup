package de.mrcjln.shutup.domain.model

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import org.cryptomator.util.SharedPreferencesHandler
import timber.log.Timber

class BluetoothConnectivity(context: Context) : Connectivity {

    private val bluetoothAdapter: BluetoothAdapter
            = (context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private val sharedPreferenceHandler: SharedPreferencesHandler
            = SharedPreferencesHandler(context)

    override fun turnOn() {
        if (sharedPreferenceHandler.shutUpBluetooth() && sharedPreferenceHandler.wasBluetoothOn()) {
            Timber.tag("BluetoothConnectivity").d("turn on")
            bluetoothAdapter.enable()
        }
    }

    override fun turnOff() {
        sharedPreferenceHandler.setBluetoothWasOn(isOn())
        if (sharedPreferenceHandler.shutUpBluetooth() && isOn()) {
            Timber.tag("BluetoothConnectivity").d("turn off")
            bluetoothAdapter.disable()
        }
    }

    override fun isOn(): Boolean = bluetoothAdapter.isEnabled
}
