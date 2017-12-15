package de.mrcjln.shutup.domain.usecase

import de.mrcjln.shutup.domain.repository.ConnectivityRepositoryImpl

class TurnOffConnectivityUseCase(
        // TODO inject interface
        private val connectivityRepository: ConnectivityRepositoryImpl
) : UseCase() {

    override fun execute() {
        connectivityRepository.turnOffConnectivity()
    }

}
