package com.nagopy.android.bashfulclock.analytics.internal

import android.content.SharedPreferences
import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.analytics.FirebaseAnalytics
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class AnalyticsImplTest {

    @Mock
    lateinit var fa: FirebaseAnalytics
    @Mock
    lateinit var sp: SharedPreferences

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test() {
        `when`(sp.all).thenReturn(mapOf("foo" to "foo_val", "bar" to "bar_val"))
        doNothing().`when`(fa).logEvent(anyString(), any())

        val analyticsImpl = AnalyticsImpl(fa)
        analyticsImpl.sendSettingChangeEvent(sp, "foo")

        val argumentKey = ArgumentCaptor.forClass(String::class.java)
        val argument = ArgumentCaptor.forClass(Bundle::class.java)
        verify(fa, times(1)).logEvent(argumentKey.capture(), argument.capture())
        assertThat(argumentKey.value, `is`("setting_changed"))
        assertThat(argument.value.size(), `is`(2))
        assertThat(argument.value["pref_key"] as String, `is`("foo"))
        assertThat(argument.value["pref_value"] as String, `is`("foo_val"))
    }
}
