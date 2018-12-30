package com.nagopy.android.bashfulclock

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import javax.inject.Inject

class RemoteConfig @Inject constructor(private val firebaseRemoteConfig: FirebaseRemoteConfig) {

    private val cache = HashMap<String, Any>()

    init {
        firebaseRemoteConfig.setDefaults(HashMap<String, Any>().apply {
            put(
                KEY_JAPANESE_ERA,
                "[{\"endTime\":1556636399999,\"startYear\":1989,\"name\":\"平成\",\"shortName\":\"H\"}]"
            )
        })
    }

    fun fetchRequest(callback: ((Boolean) -> Unit)? = null) {
        Timber.d("Fetch")
        val isUsingDeveloperMode = firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled
        val cacheExpiration: Long = if (isUsingDeveloperMode) {
            0
        } else {
            3600 // 1 hour in seconds.
        }
        firebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener {
            Timber.d("onComplete isSuccessful=%s", it.isSuccessful)
            if (it.isSuccessful) {
                firebaseRemoteConfig.activateFetched()
            }
            callback?.invoke(it.isSuccessful)
        }
    }


    fun getJapaneseEraConfig(): Collection<RemoteJapaneseEra> {
        val json = firebaseRemoteConfig.getString(KEY_JAPANESE_ERA)
        @Suppress("UNCHECKED_CAST")
        val cachedObj = cache[json] as? Collection<RemoteJapaneseEra>
        return if (cachedObj != null) {
            cachedObj
        } else {
            val obj: Collection<RemoteJapaneseEra> = Gson().fromJson(
                json,
                object : TypeToken<Collection<RemoteJapaneseEra>>() {}.type
            )
            cache[json] = obj
            obj
        }
    }

    companion object {
        const val KEY_JAPANESE_ERA = "japanese_era"
    }

    data class RemoteJapaneseEra(
        val endTime: Long,
        val startYear: Int,
        val name: String,
        val shortName: String
    )
}