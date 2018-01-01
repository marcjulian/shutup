package de.squiray.shutup.domain.repository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideConnectivityRepository(connectivityRepositoryImpl: ConnectivityRepositoryImpl): ConnectivityRepository {
        return connectivityRepositoryImpl
    }
}
