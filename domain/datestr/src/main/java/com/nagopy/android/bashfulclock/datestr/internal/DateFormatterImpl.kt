package com.nagopy.android.bashfulclock.datestr.internal

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.core.os.ConfigurationCompat
import com.nagopy.android.bashfulclock.datestr.DateFormatter
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.usersettings.UserSettings
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(
    private val userSettings: UserSettings
    , private val context: Context
    , private val japaneseEraRepository: JapaneseEraRepository
) : DateFormatter {

    private val df = SimpleDateFormat(
        userSettings.dateFormat,
        if (userSettings.inEnglish) Locale.ENGLISH else ConfigurationCompat.getLocales(context.resources.configuration)[0]
    )

    override fun format(date: Date): String {
        return formatJapaneseEra(df.format(date), date)
    }

    override fun format(formatStr: String, date: Date): String {
        val tdf = SimpleDateFormat(
            formatStr,
            if (userSettings.inEnglish) Locale.ENGLISH else ConfigurationCompat.getLocales(context.resources.configuration)[0]
        )
        return formatJapaneseEra(tdf.format(date), date)
    }

    @VisibleForTesting
    internal fun formatJapaneseEra(formattedStr: String, date: Date): String {
        return when {
            formattedStr.contains("和暦長") -> {
                val jpEra = japaneseEraRepository.getCurrentJapaneseEra(date)
                formattedStr.replace("和暦長", jpEra?.formatLong(date) ?: "")
            }
            formattedStr.contains("和暦短") -> {
                val jpEra = japaneseEraRepository.getCurrentJapaneseEra(date)
                formattedStr.replace("和暦短", jpEra?.formatShort(date) ?: "")
            }
            else -> formattedStr
        }
    }
}
