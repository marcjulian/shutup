package de.squiray.shutup.presentation.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import de.squiray.shutup.BuildConfig
import de.squiray.shutup.R
import de.squiray.shutup.presentation.service.ShutUpService
import de.squiray.shutup.util.SharedPreferencesHandler


class SettingsFragment : PreferenceFragment() {

    private val APP_VERSION_ITEM_KEY = "appVersion"
    private val STOP_APP_NOTIFICATION_KEY = "stopShutUpNotification"

    private var sharedPreferencesHandler: SharedPreferencesHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        sharedPreferencesHandler = SharedPreferencesHandler(activity)
        setupAppVersion()
    }

    override fun onResume() {
        super.onResume()
        /*
        findPreference(STOP_APP_NOTIFICATION_KEY)
                .setOnPreferenceClickListener {
                    onStopAppNotificationClicked()
                    true
                }
                */
    }

    private fun setupAppVersion() {
        val preference = findPreference(APP_VERSION_ITEM_KEY)
        val versionName = SpannableString(BuildConfig.VERSION_NAME)
        versionName.setSpan( //
                ForegroundColorSpan(ContextCompat.getColor(activity, R.color.colorPrimaryDark)), //
                0, versionName.length, 0)
        preference.summary = versionName
    }

    private fun onStopAppNotificationClicked() {
        //activity.startService(ShutUpService.stopShutUpService(activity))
        // TODO pause shut up
    }
}
