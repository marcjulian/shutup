package de.squiray.shutup.util.logging

import android.content.Context
import android.util.Log

import timber.log.Timber

class ReleaseLogger(context: Context) : Timber.Tree() {

    private val PRIORITY_NAMES = charArrayOf(//
            '?', //
            '?', //
            'V', //
            'D', //
            'I', //
            'W', //
            'E', //
            'A' //
    )

    private val log: LogRotator

    init {
        //debugMode = new SharedPreferencesHandler(context).debugMode();
        this.log = LogRotator(context)
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return debugMode || priority >= LOG_LEVEL_WHEN_DEBUG_IS_DISABLED
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        var tag = tag
        if (tag == null) {
            tag = "App"
        }
        val line = StringBuilder()
        line //
                .append(PRIORITY_NAMES[validPriority(priority)]).append('\t') //
                .append(FormattedTime.now()).append('\t') //
                .append(tag).append('\t') //
                .append(message)
        if (throwable != null) {
            line //
                    .append("\nErrorCode: ") //
                    .append(GeneratedErrorCode.of(throwable))
        }
        log.log(line.toString())
    }

    private fun validPriority(priority: Int): Int {
        return if (priority >= 1 && priority <= 7) {
            priority
        } else 0
    }

    companion object {

        private val LOG_LEVEL_WHEN_DEBUG_IS_DISABLED = Log.INFO

        @Volatile private var debugMode: Boolean = false

        fun updateDebugMode(debugMode: Boolean) {
            ReleaseLogger.debugMode = debugMode
            Timber.tag("Logging").i(if (debugMode) "Debug mode enabled" else "Debug mode disabled")
        }
    }
}
