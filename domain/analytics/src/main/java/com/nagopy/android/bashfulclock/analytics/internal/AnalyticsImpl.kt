package com.nagopy.android.bashfulclock.analytics.internal

import android.content.SharedPreferences
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.nagopy.android.bashfulclock.analytics.Analytics
import javax.inject.Inject

internal class AnalyticsImpl @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {

    override fun sendSettingChangeEvent(sharedPreferences: SharedPreferences?, key: String?) {
        val changedValue = sharedPreferences?.all?.get(key)
        changedValue?.let {
            firebaseAnalytics.logEvent("setting_changed", Bundle().apply {
                putString("pref_key", key)
                putString("pref_value", it.toString())
            })
        }
    }
}
