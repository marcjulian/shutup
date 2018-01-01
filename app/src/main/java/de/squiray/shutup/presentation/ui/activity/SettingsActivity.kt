package de.squiray.shutup.presentation.ui.activity

import de.squiray.shutup.R
import kotlinx.android.synthetic.main.layout_toolbar.*

@Activity(layout = R.layout.activity_settings)
class SettingsActivity : BaseActivity() {

    override fun setupView() {
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar.setTitle(R.string.screen_settings_title)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }
}
