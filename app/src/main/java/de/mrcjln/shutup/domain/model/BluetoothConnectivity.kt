package de.mrcjln.shutup.domain.model

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import timber.log.Timber

class BluetoothConnectivity(context: Context) : Connectivity {

    private val bluetoothAdapter: BluetoothAdapter
            = (context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    override fun turnOn() {
        Timber.tag("BluetoothConnectivity").d("turn on")
        bluetoothAdapter.enable()
    }

    override fun turnOff() {
        Timber.tag("BluetoothConnectivity").d("turn off")
        bluetoothAdapter.disable()
    }

    override fun isOn(): Boolean = bluetoothAdapter.isEnabled
}
