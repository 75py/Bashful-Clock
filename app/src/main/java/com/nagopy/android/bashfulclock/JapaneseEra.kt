package com.nagopy.android.bashfulclock

import java.util.*
import javax.inject.Inject

class JapaneseEra @Inject constructor(private val remoteConfig: RemoteConfig) {

    fun formatLong(date: Date): String {
        val era = remoteConfig.getJapaneseEraConfig().firstOrNull { date.time <= it.endTime } ?: return ""
        val c = Calendar.getInstance().apply { time = date }
        val y = c.get(Calendar.YEAR)
        val jy = y - era.startYear + 1
        return if (jy == 1) {
            "${era.name}元年"
        } else {
            "${era.name}${jy}年"
        }
    }

    fun formatShort(date: Date): String {
        val era = remoteConfig.getJapaneseEraConfig().firstOrNull { date.time <= it.endTime } ?: return ""
        val c = Calendar.getInstance().apply { time = date }
        val y = c.get(Calendar.YEAR)
        val jy = y - era.startYear + 1
        return "${era.shortName}$jy"
    }
}
