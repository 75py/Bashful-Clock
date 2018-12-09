package com.nagopy.android.bashfulclock.app

import com.nagopy.android.bashfulclock.AppComponent
import com.nagopy.android.bashfulclock.BuildConfig
import com.nagopy.android.bashfulclock.DaggerAppComponent
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    lateinit var component: AppComponent

    override fun onCreate() {
        component = DaggerAppComponent.builder().create(this) as AppComponent

        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        OverlayViewManager.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }

}
