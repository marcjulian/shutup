package de.squiray.shutup.presentation.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import de.squiray.shutup.domain.repository.RepositoryModule
import de.squiray.shutup.presentation.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AppModule::class),
    (ProviderModule::class),
    (ConnectivityModule::class),
    (ThreadModule::class),
    (RepositoryModule::class),
    (ActivityModule::class),
    (FragmentModule::class)])
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
