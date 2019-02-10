package com.nagopy.android.bashfulclock.data.remoteconfig.internal

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig.Companion.KEY_JAPANESE_ERA
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfigJapaneseEras
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class RemoteConfigImpl @Inject constructor(private val firebaseRemoteConfig: FirebaseRemoteConfig) :
    RemoteConfig {

    private val cache = HashMap<String, Any?>()

    init {
        firebaseRemoteConfig.setDefaults(HashMap<String, Any>().apply {
            put(
                KEY_JAPANESE_ERA,
                "[{\"endTime\":1556636399999,\"startYear\":1989,\"name\":\"平成\",\"shortName\":\"H\"}]"
            )
        })
    }

    override fun fetch(callback: ((Boolean) -> Unit)?) {
//        Timber.d("Fetch")
        val isUsingDeveloperMode = firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled
        val cacheExpiration: Long = if (isUsingDeveloperMode) {
            0
        } else {
            3600 // 1 hour in seconds.
        }
        firebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener {
            //            Timber.d("onComplete isSuccessful=%s", it.isSuccessful)
            if (it.isSuccessful) {
                firebaseRemoteConfig.activateFetched()
            }
            callback?.invoke(it.isSuccessful)
        }
    }

    override fun getJapaneseEras(): RemoteConfigJapaneseEras? {
        val json = firebaseRemoteConfig.getString(KEY_JAPANESE_ERA)
        @Suppress("UNCHECKED_CAST")
        val cachedObj = cache[json] as? RemoteConfigJapaneseEras
        return if (cachedObj != null) {
            cachedObj
        } else {
            val obj = Json.parse(RemoteConfigJapaneseEras.serializer(), json)
            cache[json] = obj
            obj
        }
    }
}
