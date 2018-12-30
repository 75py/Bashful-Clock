package com.nagopy.android.bashfulclock

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateFormatter @Inject constructor(
    private val userSettings: UserSettings
    , private val context: Context
    , private val japaneseEra: JapaneseEra
) {

    private val df = SimpleDateFormat(
        userSettings.dateFormat,
        if (userSettings.inEnglish) Locale.ENGLISH else ConfigurationCompat.getLocales(context.resources.configuration)[0]
    )

    fun format(date: Date): String {
        return formatJapaneseEra(df.format(date), date)
    }

    fun format(formatStr: String, date: Date): String {
        val tdf = SimpleDateFormat(
            formatStr,
            if (userSettings.inEnglish) Locale.ENGLISH else ConfigurationCompat.getLocales(context.resources.configuration)[0]
        )
        return formatJapaneseEra(tdf.format(date), date)
    }

    private fun formatJapaneseEra(formattedStr: String, date: Date): String {
        return when {
            formattedStr.contains("和暦長") -> {
                val jpEra = japaneseEra.formatLong(date)
                formattedStr.replace("和暦長", jpEra)
            }
            formattedStr.contains("和暦短") -> {
                val jpEra = japaneseEra.formatShort(date)
                formattedStr.replace("和暦短", jpEra)
            }
            else -> formattedStr
        }
    }
}
