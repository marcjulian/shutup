package de.squiray.shutup.presentation.ui.view

import android.support.v4.app.DialogFragment

interface View : MessageDisplay, ErrorDisplay, ActivityHolder {
    fun showDialog(dialog: DialogFragment)

    fun closeDialog()

    fun finish()
}
