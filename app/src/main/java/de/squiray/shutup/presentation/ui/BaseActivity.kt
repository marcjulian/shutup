package de.squiray.shutup.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import de.squiray.shutup.R

abstract class BaseActivity : AppCompatActivity() {

    private val NO_MENU = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentLayout())
        setupView()
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
}
