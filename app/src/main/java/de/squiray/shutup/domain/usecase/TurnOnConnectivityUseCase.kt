package de.squiray.shutup.domain.usecase

import de.squiray.shutup.domain.repository.ConnectivityRepository
import javax.inject.Inject

class TurnOnConnectivityUseCase @Inject constructor(
        private val connectivityRepository: ConnectivityRepository
) : UseCase() {
    override fun execute() {
        connectivityRepository.turnOnConnectivity()
    }
}
