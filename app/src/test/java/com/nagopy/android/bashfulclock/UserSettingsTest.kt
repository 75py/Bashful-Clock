package com.nagopy.android.bashfulclock

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSettingsTest {

    lateinit var userSettings: UserSettings

    lateinit var sp: SharedPreferences
    lateinit var res: Resources

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        sp = app.getSharedPreferences("test", Context.MODE_PRIVATE).apply {
            edit().clear().commit()
        }
        res = app.resources
        userSettings = UserSettings(sp, res)
    }

    @Test
    fun getInEnglish() {
        Assert.assertThat(userSettings.inEnglish, `is`(false))

        sp.edit().putBoolean(res.getString(R.string.pref_key_date_format_in_english), true).commit()
        Assert.assertThat(userSettings.inEnglish, `is`(true))
    }

    @Test
    fun getDuration() {
        Assert.assertThat(userSettings.duration, `is`("4.0".toFloat()))

        sp.edit().putString(res.getString(R.string.pref_key_duration), "3.0").commit()
        Assert.assertThat(userSettings.duration, `is`("3.0".toFloat()))
    }

    @Test
    fun getDateFormat() {
        Assert.assertThat(userSettings.dateFormat, `is`(res.getString(R.string.pref_def_value_date_format)))

        sp.edit().putString(res.getString(R.string.pref_key_date_format), "foo").commit()
        Assert.assertThat(userSettings.dateFormat, `is`("foo"))
    }

    @Test
    fun getFadeOut() {
        Assert.assertThat(userSettings.fadeOut, `is`(true))

        sp.edit().putBoolean(res.getString(R.string.pref_key_fadeout), false).commit()
        Assert.assertThat(userSettings.fadeOut, `is`(false))
    }

    @Test
    fun getTextSize() {
    }

    @Test
    fun getTextSizeSmall() {
    }

    @Test
    fun getTextSizeMedium() {
    }

    @Test
    fun getTextSizeLarge() {
    }

    @Test
    fun getTextSizeExtraLarge() {
    }

    @Test
    fun isUserSettingsKey() {
    }

    @Test
    fun getY() {
    }

    @Test
    fun setY() {
    }
}