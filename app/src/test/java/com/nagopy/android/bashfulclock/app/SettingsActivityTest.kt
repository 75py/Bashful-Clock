package com.nagopy.android.bashfulclock.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nagopy.android.bashfulclock.R
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<SettingsActivity> = ActivityTestRule(SettingsActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun aaa() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertThat(context.getString(R.string.app_name), CoreMatchers.`is`("Bashful Clock"))
//        onData(allOf(`is`(instanceOf(Preference::class.java)), withKey(activityRule.activity.getString(R.string.pref_key_fadeout))))
//                .check(matches(isDisplayed()))
//                .check(matches(isChecked()))
    }
}