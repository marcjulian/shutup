package de.squiray.shutup.domain.repository

interface ConnectivityRepository {
    fun turnOffConnectivity()

    fun turnOnConnectivity()
}
