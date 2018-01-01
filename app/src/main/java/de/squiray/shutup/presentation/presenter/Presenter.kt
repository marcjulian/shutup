package de.squiray.shutup.presentation.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import de.squiray.shutup.presentation.ui.view.ActivityHolder
import de.squiray.shutup.presentation.ui.view.View


abstract class Presenter<V : View> : ActivityHolder {

    lateinit var view: V

    private var isPaused: Boolean = false

    fun resume() {
        isPaused = false
        resumed()
    }

    fun pause() {
        isPaused = true
        paused()
    }

    fun destroy() {
        destroyed()
    }

    open fun resumed() {

    }

    open fun paused() {

    }

    open fun destroyed() {

    }

    fun startIntent(intent: Intent) {
        activity().startActivity(intent)
    }

    override fun activity(): Activity = view.activity()

    override fun context(): Context = view.context()

    fun finish() {
        view.finish()
    }
}
