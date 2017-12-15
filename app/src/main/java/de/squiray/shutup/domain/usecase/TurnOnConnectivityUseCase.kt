package de.squiray.shutup.domain.usecase

import de.squiray.shutup.domain.repository.ConnectivityRepositoryImpl

class TurnOnConnectivityUseCase(val connectivityRepositoryImpl: ConnectivityRepositoryImpl)
    : UseCase() {
    override fun execute() {
        connectivityRepositoryImpl.turnOnConnectivity()
    }
}
