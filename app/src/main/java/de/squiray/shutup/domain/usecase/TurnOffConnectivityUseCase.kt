package de.squiray.shutup.domain.usecase

import de.squiray.shutup.domain.repository.ConnectivityRepositoryImpl

class TurnOffConnectivityUseCase(
        // TODO inject interface
        private val connectivityRepository: ConnectivityRepositoryImpl
) : UseCase() {

    override fun execute() {
        connectivityRepository.turnOffConnectivity()
    }

}
