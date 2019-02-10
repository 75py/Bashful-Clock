package com.nagopy.android.bashfulclock.data.remoteconfig.internal

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import javax.inject.Inject

internal class RemoteConfigImpl @Inject constructor(private val firebaseRemoteConfig: FirebaseRemoteConfig) :
    RemoteConfig {
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
}