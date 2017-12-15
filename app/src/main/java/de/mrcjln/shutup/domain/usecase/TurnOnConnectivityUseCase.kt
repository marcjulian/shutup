package de.mrcjln.shutup.domain.usecase

import de.mrcjln.shutup.domain.repository.ConnectivityRepositoryImpl

class TurnOnConnectivityUseCase(val connectivityRepositoryImpl: ConnectivityRepositoryImpl)
    : UseCase() {
    override fun execute() {
        connectivityRepositoryImpl.turnOnConnectivity()
    }
}
