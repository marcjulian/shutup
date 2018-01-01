package de.squiray.shutup.domain.repository

import de.squiray.shutup.domain.model.BluetoothConnectivity
import de.squiray.shutup.domain.model.Connectivity
import de.squiray.shutup.domain.model.WifiConnectivity
import javax.inject.Inject

class ConnectivityRepositoryImpl @Inject constructor(
        wifiConnectivity: WifiConnectivity,
        bluetoothConnectivity: BluetoothConnectivity
) : ConnectivityRepository {

    private val connectivity: Array<Connectivity> =
            arrayOf(
                    wifiConnectivity,
                    bluetoothConnectivity
            )

    override fun turnOffConnectivity() {
        connectivity.forEach { connectivity -> connectivity.turnOff() }
    }

    override fun turnOnConnectivity() {
        connectivity.forEach { connectivity -> connectivity.turnOn() }
    }
}
