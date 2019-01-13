package com.nagopy.android.bashfulclock.infra.japaneseera

import com.nagopy.android.bashfulclock.domain.japaneseera.JapaneseEra
import com.nagopy.android.bashfulclock.domain.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.infra.remoteconfig.RemoteConfig
import java.util.*
import javax.inject.Inject

class JapaneseEraRepositoryImpl @Inject constructor(private val remoteConfig: RemoteConfig) :
    JapaneseEraRepository {

    override fun getCurrentJapaneseEra(date: Date): JapaneseEra? {
        val rc = remoteConfig.getJapaneseEraConfig().firstOrNull { date.time <= it.endTime }
        return if (rc == null) {
            null
        } else {
            JapaneseEra(rc.startYear, rc.name, rc.shortName)
        }
    }

}
