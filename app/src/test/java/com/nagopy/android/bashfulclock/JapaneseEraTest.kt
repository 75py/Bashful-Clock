package com.nagopy.android.bashfulclock

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class JapaneseEraTest {

    lateinit var japaneseEra: JapaneseEra

    @Mock
    lateinit var remoteConfig: RemoteConfig

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        japaneseEra = JapaneseEra(remoteConfig)

        val conf = arrayListOf(
            RemoteConfig.RemoteJapaneseEra(1556636399999, 1989, "平成", "H"),
            RemoteConfig.RemoteJapaneseEra(2556636399999, 2019, "ネオ平成", "NH")
        )
        Mockito.`when`(remoteConfig.getJapaneseEraConfig()).thenReturn(conf)
    }

    @Test
    fun formatLong() {
        Assert.assertThat(japaneseEra.formatLong(Date(1556636399999)), CoreMatchers.`is`("平成31年"))
        Assert.assertThat(japaneseEra.formatLong(Date(1556637000000)), CoreMatchers.`is`("ネオ平成元年"))
        Assert.assertThat(japaneseEra.formatLong(Date(2556637000000)), CoreMatchers.`is`(""))
    }
    @Test
    fun formatShort() {
        Assert.assertThat(japaneseEra.formatShort(Date(1556636399999)), CoreMatchers.`is`("H31"))
        Assert.assertThat(japaneseEra.formatShort(Date(1556637000000)), CoreMatchers.`is`("NH1"))
        Assert.assertThat(japaneseEra.formatShort(Date(2556637000000)), CoreMatchers.`is`(""))
    }
}
