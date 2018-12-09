package com.nagopy.android.bashfulclock.app

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import com.nagopy.android.bashfulclock.DateFormatter
import java.util.*
import javax.inject.Inject

class DateFormatListPreference : ListPreference {

    @Inject
    lateinit var dateFormatter: DateFormatter

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    override fun onAttached() {
        super.onAttached()
        (context.applicationContext as App).component.inject(this)
    }

    override fun getEntries(): Array<CharSequence> {
        val date = Date()
        return super.getEntries().map {
            dateFormatter.format(it.toString(), date)
        }.toTypedArray()
    }

}