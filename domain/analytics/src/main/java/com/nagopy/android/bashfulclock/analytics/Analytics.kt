package com.nagopy.android.bashfulclock.analytics

import android.content.SharedPreferences

interface Analytics {
    fun sendSettingChangeEvent(sharedPreferences: SharedPreferences?, key: String?)
}
