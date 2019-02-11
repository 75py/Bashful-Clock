package com.nagopy.android.bashfulclock.datestr.internal

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEra
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.usersettings.UserSettings
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class DateFormatterImplTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var userSettings: UserSettings

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var japaneseEraRepository: JapaneseEraRepository

    lateinit var dateFormatter: DateFormatterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        assertThat(userSettings, `is`(notNullValue()))
        assertThat(japaneseEraRepository, `is`(notNullValue()))

        `when`(userSettings.dateFormat).thenReturn("yyyy/MM/dd")
        `when`(userSettings.inEnglish).thenReturn(true)

        val context = ApplicationProvider.getApplicationContext<Context>()
        assertThat(context, `is`(notNullValue()))

        dateFormatter = DateFormatterImpl(userSettings, context, japaneseEraRepository)
    }

    @Test
    fun testFormatJapaneseEra() {
        val date = SimpleDateFormat("yyyy/MM/dd").parse("2019/01/01")
        val je = mock(JapaneseEra::class.java)
        `when`(japaneseEraRepository.getCurrentJapaneseEra(date)).thenReturn(je)
        `when`(je.formatLong(date)).thenReturn("平成31年")
        `when`(je.formatShort(date)).thenReturn("H31")

        assertThat(
            dateFormatter.formatJapaneseEra("2019/01/01 和暦長", date),
            `is`("2019/01/01 平成31年")
        )
        assertThat(
            dateFormatter.formatJapaneseEra("2019/01/01 和暦短", date),
            `is`("2019/01/01 H31")
        )
    }

}
