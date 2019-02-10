package com.nagopy.android.bashfulclock.data.remoteconfig

interface RemoteConfig {
    fun fetch(callback: ((Boolean) -> Unit)? = null)

    fun getJapaneseEras(): RemoteConfigJapaneseEras?

    companion object {
        const val KEY_JAPANESE_ERA = "japanese_era"
    }
}
