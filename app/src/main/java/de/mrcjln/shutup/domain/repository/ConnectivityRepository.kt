package de.mrcjln.shutup.domain.repository

interface ConnectivityRepository {
    fun turnOffConnectivity()

    fun turnOnConnectivity()
}
