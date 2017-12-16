package de.squiray.shutup

import android.app.Application
import android.content.Intent
import android.os.Build
import de.squiray.shutup.presentation.service.ShutUpService
import de.squiray.shutup.util.logging.CrashLogging
import de.squiray.shutup.util.logging.DebugLogger
import de.squiray.shutup.util.logging.ReleaseLogger
import timber.log.Timber


class ShutUpApp : Application() {

    private var shutUpService: Intent? = null

    override fun onCreate() {
        super.onCreate()
        setupLogging()
        Timber.tag("App").i("Shut up v%s (%d) started on android %s / API%d using a %s", //
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, //
                Build.VERSION.RELEASE, Build.VERSION.SDK_INT, //
                Build.MODEL)
        startShutUpService()
    }

    private fun startShutUpService() {
        shutUpService = Intent(this, ShutUpService::class.java)
        startService(shutUpService)
    }

    private fun setupLogging() {
        setupLoggingFramework()
        CrashLogging.setup()
    }

    private fun setupLoggingFramework() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugLogger())
        }
        Timber.plant(ReleaseLogger(applicationContext))
    }

}
