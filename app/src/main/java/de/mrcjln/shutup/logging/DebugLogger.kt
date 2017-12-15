package de.mrcjln.shutup.logging

import timber.log.Timber

class DebugLogger: Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        var message = message
        if (t != null) {
            message = message + "\nErrorCode: " + GeneratedErrorCode.of(t)
        }
        super.log(priority, tag, message, t)
    }
}
