package com.nagopy.android.bashfulclock.domain.japaneseera

import java.util.*

interface JapaneseEraRepository {

    fun getCurrentJapaneseEra(date: Date): JapaneseEra?

}
