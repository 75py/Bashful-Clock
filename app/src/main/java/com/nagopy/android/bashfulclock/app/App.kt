package com.nagopy.android.bashfulclock.app

import com.crashlytics.android.Crashlytics
import com.nagopy.android.bashfulclock.AppComponent
import com.nagopy.android.bashfulclock.BuildConfig
import com.nagopy.android.bashfulclock.DaggerAppComponent
import com.nagopy.android.bashfulclock.infra.remoteconfig.RemoteConfig
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {

    lateinit var component: AppComponent

    @Inject
    lateinit var remoteConfig: RemoteConfig

    override fun onCreate() {
        component = DaggerAppComponent.builder().create(this) as AppComponent

        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        remoteConfig.fetchRequest()
        Fabric.with(this, Crashlytics())
        OverlayViewManager.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}
