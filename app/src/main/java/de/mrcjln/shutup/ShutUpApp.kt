package de.mrcjln.shutup

import android.app.Application
import android.content.Intent
import de.mrcjln.shutup.logging.DebugLogger
import de.mrcjln.shutup.presentation.service.ShutUpService
import timber.log.Timber


class ShutUpApp : Application() {

    private var shutUpService: Intent? = null

    override fun onCreate() {
        super.onCreate()
        setupLogging()
        startShutUpService()
    }

    private fun startShutUpService() {
        shutUpService = Intent(this, ShutUpService::class.java)
        startService(shutUpService)
    }

    private fun setupLogging() {
        setupLoggingFramework()
    }

    private fun setupLoggingFramework() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugLogger())
        }
        // TODO
        // Timber.plant(ReleaseLogger(applicationContext))
    }

}
