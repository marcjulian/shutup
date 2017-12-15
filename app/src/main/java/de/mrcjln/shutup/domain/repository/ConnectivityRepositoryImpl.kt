package de.mrcjln.shutup.domain.repository

import android.content.Context
import de.mrcjln.shutup.domain.model.BluetoothConnectivity
import de.mrcjln.shutup.domain.model.Connectivity
import de.mrcjln.shutup.domain.model.WifiConnectivity

class ConnectivityRepositoryImpl(context: Context) : ConnectivityRepository {

    private val connectivity: Array<Connectivity> =
            // TODO add GPS
            arrayOf(
                    WifiConnectivity(context),
                    BluetoothConnectivity(context)
            )

    override fun turnOffConnectivity() {
        connectivity.forEach { connectivity -> connectivity.turnOff() }
    }

    override fun turnOnConnectivity() {
        connectivity.forEach { connectivity -> connectivity.turnOn() }
    }
}
