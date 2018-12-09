package com.nagopy.android.bashfulclock.app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.nagopy.android.bashfulclock.R
import com.nagopy.android.bashfulclock.UserSettings
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsActivity : DaggerAppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var overlayViewManager: OverlayViewManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var userSettings: UserSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000L)

            if (overlayViewManager.canDrawOverlays()) {
                MainService.start(this@SettingsActivity)
            } else {
                overlayViewManager.showPermissionRequestDialog(supportFragmentManager, R.string.app_name)
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if (overlayViewManager.canDrawOverlays() && userSettings.isUserSettingsKey(key)) {
            GlobalScope.launch(Dispatchers.Main) {
                delay(200L)
                MainService.restart(this@SettingsActivity)
                delay(200L)
                MainService.show(this@SettingsActivity)
            }
        }
    }

    class GeneralPreferenceFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.pref_general)
        }
    }
}