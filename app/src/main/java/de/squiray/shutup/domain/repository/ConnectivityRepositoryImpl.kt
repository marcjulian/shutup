package de.squiray.shutup.domain.repository

import android.content.Context
import de.squiray.shutup.domain.model.BluetoothConnectivity
import de.squiray.shutup.domain.model.Connectivity
import de.squiray.shutup.domain.model.WifiConnectivity

class ConnectivityRepositoryImpl(context: Context) : ConnectivityRepository {

    private val connectivity: Array<Connectivity> =
            arrayOf(
                    WifiConnectivity(context),
                    BluetoothConnectivity(context)
                    //GpsConnectivity(context)
            )

    override fun turnOffConnectivity() {
        connectivity.forEach { connectivity -> connectivity.turnOff() }
    }

    override fun turnOnConnectivity() {
        connectivity.forEach { connectivity -> connectivity.turnOn() }
    }
}
