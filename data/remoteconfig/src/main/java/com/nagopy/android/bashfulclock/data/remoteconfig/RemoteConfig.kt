package com.nagopy.android.bashfulclock.data.remoteconfig

interface RemoteConfig {
    fun fetch(callback: ((Boolean) -> Unit)? = null)
}
