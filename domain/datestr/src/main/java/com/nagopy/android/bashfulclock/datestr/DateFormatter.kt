package com.nagopy.android.bashfulclock.datestr

import java.util.*

interface DateFormatter {

    fun format(date: Date): String

    fun format(formatStr: String, date: Date): String

}
