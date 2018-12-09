package com.nagopy.android.bashfulclock

import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import timber.log.Timber
import javax.inject.Inject


class UserSettings @Inject constructor(private val sharedPreferences: SharedPreferences, private val resources: Resources) {

    private val keyInEnglish = resources.getString(R.string.pref_key_date_format_in_english)
    private val defValueInEnglish = resources.getBoolean(R.bool.pref_default_value_date_format_in_english)
    val inEnglish: Boolean
        get() = sharedPreferences.getBoolean(keyInEnglish, defValueInEnglish)

    private val keyDuration = resources.getString(R.string.pref_key_duration)
    private val defValueDuration = resources.getString(R.string.pref_default_value_duration)
    val duration: Float
        get() = try {
            sharedPreferences.getString(keyDuration, defValueDuration)!!.toFloat()
        } catch (e: NumberFormatException) {
            sharedPreferences.edit().putString(keyDuration, defValueDuration).apply()
            defValueDuration.toFloat()
        }

    private val keyDateFormat = resources.getString(R.string.pref_key_date_format)
    private val defValueDateFormat = resources.getString(R.string.pref_def_value_date_format)
    val dateFormat: String
        get() = sharedPreferences.getString(keyDateFormat, defValueDateFormat)!!


    private val keyFadeOut = resources.getString(R.string.pref_key_fadeout)
    private val defValueFadeOut = resources.getBoolean(R.bool.pref_default_value_fadeout)
    val fadeOut: Boolean
        get() = sharedPreferences.getBoolean(keyFadeOut, defValueFadeOut)

    private val keyTextSize = resources.getString(R.string.pref_key_text_size)
    private val defValueTextSize = resources.getString(R.string.pref_default_value_text_size)
    val textSize: String
        get() = sharedPreferences.getString(keyTextSize, defValueTextSize)!!
    val textSizeSmall: String = resources.getString(R.string.pref_entryValues_text_size_small)
    val textSizeMedium: String = resources.getString(R.string.pref_entryValues_text_size_medium)
    val textSizeLarge: String = resources.getString(R.string.pref_entryValues_text_size_large)
    val textSizeExtraLarge: String = resources.getString(R.string.pref_entryValues_text_size_xlarge)

    fun isUserSettingsKey(key: String?) = when (key) {
        keyInEnglish, keyDuration, keyFadeOut, keyDateFormat, keyTextSize -> true
        else -> false
    }

    var y: Int
        get() = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> verticalY
            Configuration.ORIENTATION_LANDSCAPE -> horizontalY
            else -> verticalY
        }
        set(value) = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> verticalY = value
            Configuration.ORIENTATION_LANDSCAPE -> horizontalY = value
            else -> verticalY = value
        }

    private var verticalY: Int
        get() {
            Timber.d("getVerticalY()")
            return sharedPreferences.getInt(KEY_VERTICAL_Y, 100)
        }
        set(value) {
            Timber.d("setVerticalY()")
            sharedPreferences.edit().putInt(KEY_VERTICAL_Y, value).apply()
        }

    private var horizontalY: Int
        get() {
            Timber.d("getHorizontalY()")
            return sharedPreferences.getInt(KEY_HORIZONTAL_Y, 100)
        }
        set(value) {
            Timber.d("setHorizontalY()")
            sharedPreferences.edit().putInt(KEY_HORIZONTAL_Y, value).apply()
        }

    companion object {
        private const val KEY_VERTICAL_Y = "verticalY"
        private const val KEY_HORIZONTAL_Y = "horizontalY"
    }
}
