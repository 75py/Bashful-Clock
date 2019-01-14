package com.nagopy.android.bashfulclock.domain.japaneseera

import java.util.*

class JapaneseEra(
    private val startYear: Int,
    private val name: String,
    private val shortName: String
) {

    fun formatLong(date: Date): String {
        val c = Calendar.getInstance().apply { time = date }
        val y = c.get(Calendar.YEAR)
        val jy = y - startYear + 1
        return if (jy == 1) {
            "${name}元年"
        } else {
            "${name}${jy}年"
        }
    }

    fun formatShort(date: Date): String {
        val c = Calendar.getInstance().apply { time = date }
        val y = c.get(Calendar.YEAR)
        val jy = y - startYear + 1
        return "${shortName}$jy"
    }

}
