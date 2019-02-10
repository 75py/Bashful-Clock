package com.nagopy.android.bashfulclock.analytics

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import com.nagopy.android.bashfulclock.data.remoteconfig.internal.RemoteConfigImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object RemoteConfigModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(context: Context): FirebaseRemoteConfig {
        if (FirebaseApp.getApps(context).isNullOrEmpty()) {
            FirebaseApp.initializeApp(context)
        }
        return FirebaseRemoteConfig.getInstance()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRemoteConfig(impl: RemoteConfigImpl): RemoteConfig = impl

}
