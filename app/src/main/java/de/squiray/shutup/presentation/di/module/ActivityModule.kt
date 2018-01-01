package de.squiray.shutup.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.squiray.shutup.presentation.ui.activity.SettingsActivity
import de.squiray.shutup.presentation.ui.activity.ShutUpActivity

@Module
abstract class ActivityModule {

    // TODO add splash activity
    //@ContributesAndroidInjector
    //abstract fun contributeSplashActivityInjector(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeShutUpActivityInjector(): ShutUpActivity

    @ContributesAndroidInjector
    abstract fun contributeSettingsActivityInjector(): SettingsActivity

}
