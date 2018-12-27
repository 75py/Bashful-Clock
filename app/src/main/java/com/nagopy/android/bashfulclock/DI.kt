package com.nagopy.android.bashfulclock

import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.nagopy.android.bashfulclock.app.App
import com.nagopy.android.bashfulclock.app.DateFormatListPreference
import com.nagopy.android.bashfulclock.app.MainService
import com.nagopy.android.bashfulclock.app.SettingsActivity
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: App): Context = application

    @Singleton
    @Provides
    fun provideResources(application: App): Resources = application.resources

    @Singleton
    @Provides
    fun provideSharedPreferences(application: App) = PreferenceManager.getDefaultSharedPreferences(application)

    @Singleton
    @Provides
    fun provideNotificationManager(application: App) = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Singleton
    @Provides
    fun provideOverlayViewManager() = OverlayViewManager.getInstance()!!

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(application: App) = FirebaseAnalytics.getInstance(application)

}

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSettingsActivity(): SettingsActivity

}

@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun contributeMainService(): MainService

}

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class, // これが必要
    AppModule::class, ActivityModule::class, ServiceModule::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

    fun inject(dateFormatListPreference: DateFormatListPreference)
}