package de.squiray.shutup.presentation.di.module

import android.app.Application
import android.content.Context
import android.support.v4.app.NotificationManagerCompat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.squiray.shutup.presentation.service.ShutUpService

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @ContributesAndroidInjector
    abstract fun contributeShutUpServiceInjector(): ShutUpService

}
