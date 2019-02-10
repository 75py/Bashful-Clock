package com.nagopy.android.bashfulclock.japaneseera.internal

import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEra
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import java.util.*
import javax.inject.Inject


class JapaneseEraRepositoryImpl @Inject constructor(private val remoteConfig: RemoteConfig) :
    JapaneseEraRepository {

    override fun getCurrentJapaneseEra(date: Date): JapaneseEra? {
        val rc = remoteConfig.getJapaneseEras()?.eras?.firstOrNull { date.time <= it.endTime }
        return if (rc == null) {
            null
        } else {
            JapaneseEra(rc.startYear, rc.name, rc.shortName)
        }
    }

}
