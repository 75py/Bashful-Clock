package com.nagopy.android.bashfulclock

import android.content.SharedPreferences
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsWrapper @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) {

    fun sendSettingChangeEvent(sharedPreferences: SharedPreferences?, key: String?) {
        val changedValue = sharedPreferences?.all?.get(key)
        changedValue?.let {
            firebaseAnalytics.logEvent("setting_changed", Bundle().apply {
                putString("pref_key", key)
                putString("pref_value", it.toString())
            })
        }
    }
}
