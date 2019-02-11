package com.nagopy.android.bashfulclock.japaneseera

import java.util.*

interface JapaneseEraRepository {

    fun getCurrentJapaneseEra(date: Date): JapaneseEra?

}
