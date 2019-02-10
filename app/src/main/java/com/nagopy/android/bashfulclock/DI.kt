package com.nagopy.android.bashfulclock

import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import androidx.preference.PreferenceManager
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nagopy.android.bashfulclock.analytics.Analytics
import com.nagopy.android.bashfulclock.analytics.AnalyticsComponent
import com.nagopy.android.bashfulclock.analytics.RemoteConfigComponent
import com.nagopy.android.bashfulclock.app.*
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import com.nagopy.android.bashfulclock.infra.InfraModule
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraComponent
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import com.nagopy.android.overlayviewmanager.OverlayViewManager
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module
object AnalyticsComponentModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideAnalytics(context: Context): Analytics {
        return AnalyticsComponent.builder()
            .context(context)
            .build()
            .analytics()
    }
}

@Module
object RemoteConfigComponentModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideRemoteConfig(context: Context): RemoteConfig {
        return RemoteConfigComponent.builder()
            .context(context)
            .build()
            .remoteConfig()
    }
}

@Module(includes = [RemoteConfigComponentModule::class])
object JapaneseEraRepositoryComponentModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideJapaneseEraRepository(remoteConfig: RemoteConfig): JapaneseEraRepository {
        return JapaneseEraComponent.builder()
            .remoteConfig(remoteConfig)
            .build()
            .japaneseEraRepository()
    }
}

@Module(includes = [InfraModule::class, AnalyticsComponentModule::class, RemoteConfigComponentModule::class, JapaneseEraRepositoryComponentModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: App): Context = application

    @Singleton
    @Provides
    fun provideResources(application: App): Resources = application.resources

    @Singleton
    @Provides
    fun provideSharedPreferences(application: App) = PreferenceManager.getDefaultSharedPreferences(application)!!

    @Singleton
    @Provides
    fun provideNotificationManager(application: App) =
        application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Singleton
    @Provides
    fun provideOverlayViewManager() = OverlayViewManager.getInstance()!!

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(application: App): FirebaseRemoteConfig {
        if (FirebaseApp.getApps(application).isNullOrEmpty()) {
            FirebaseApp.initializeApp(application)
        }
        return FirebaseRemoteConfig.getInstance()
    }

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

    @ContributesAndroidInjector
    abstract fun contributeRemoteConfigService(): RemoteConfigService

}

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class, // これが必要
        AppModule::class, ActivityModule::class, ServiceModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

    fun inject(dateFormatListPreference: DateFormatListPreference)
}
