package de.squiray.shutup.presentation.ui.activity

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Menu
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import de.squiray.shutup.R
import de.squiray.shutup.presentation.presenter.Presenter
import de.squiray.shutup.presentation.ui.view.View
import de.squiray.shutup.util.extensions.toast

abstract class BaseActivity : DaggerAppCompatActivity(), View {

    private val NO_MENU = -1

    private val ACTIVE_DIALOG = "activeDialog"

    private var currentDialog: DialogFragment? = null

    private var presenter: Presenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentLayout())

        // TODO add presenter for each activity
        //presenter = Activities.initializePresenter(this)

        setupView()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (presenter != null) {
            presenter!!.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (presenter != null) {
            presenter!!.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destroy()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuResource = getMenuResource()
        if (menuResource != NO_MENU) {
            menuInflater.inflate(menuResource, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onMenuItemSelected(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    open fun onMenuItemSelected(itemId: Int): Boolean {
        if (itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    private fun getMenuResource(): Int {
        return javaClass.getAnnotation(Activity::class.java).menu
    }

    abstract fun setupView()

    private fun contentLayout(): Int {
        val layout = javaClass.getAnnotation(Activity::class.java).layout
        return if (layout == -1) {
            R.layout.activity_layout
        } else {
            layout
        }
    }

    override fun context(): Context = this

    fun getCurrentFragment(fragmentContainer: Int): Fragment =
            fragmentManager.findFragmentById(fragmentContainer)

    override fun showError(messageId: Int) = toast(getString(messageId))


    override fun showMessage(messageId: Int, vararg args: Any) = showMessage(getString(messageId), args)


    override fun showError(message: String) = toast(message)


    override fun activity(): android.app.Activity = this

    override fun showMessage(message: String, vararg args: Any) = toast(java.lang.String.format(message, args))

    override fun showDialog(dialog: DialogFragment) {
        closeDialog()
        currentDialog = dialog
        dialog.show(supportFragmentManager, ACTIVE_DIALOG)
    }

    override fun closeDialog() {
        if (currentDialog != null) {
            currentDialog!!.dismissAllowingStateLoss()
            currentDialog = null
        }
    }
}
