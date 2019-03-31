package com.nagopy.android.bashfulclock

import android.app.NotificationManager
import android.content.Context
import androidx.preference.PreferenceManager
import com.nagopy.android.bashfulclock.analytics.Analytics
import com.nagopy.android.bashfulclock.analytics.AnalyticsComponent
import com.nagopy.android.bashfulclock.analytics.RemoteConfigComponent
import com.nagopy.android.bashfulclock.app.*
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import com.nagopy.android.bashfulclock.datestr.DateFormatter
import com.nagopy.android.bashfulclock.datestr.DateStrComponent
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraComponent
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.usersettings.UserSettings
import com.nagopy.android.bashfulclock.usersettings.UserSettingsComponent
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

@Module
object UserSettingsComponentModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideUserSettings(context: Context): UserSettings {
        return UserSettingsComponent.builder()
            .context(context)
            .build()
            .userSettings()
    }
}

@Module(includes = [UserSettingsComponentModule::class, JapaneseEraRepositoryComponentModule::class])
object DateStrComponentModule {
    @JvmStatic
    @Provides
    fun provideDateFormatter(
        context: Context,
        userSettings: UserSettings,
        japaneseEraRepository: JapaneseEraRepository
    ): DateFormatter {
        return DateStrComponent.builder()
            .context(context)
            .userSettings(userSettings)
            .japaneseEraRepository(japaneseEraRepository)
            .build()
            .dateFormatter()
    }
}

@Module(
    includes = [
        AnalyticsComponentModule::class,
        RemoteConfigComponentModule::class,
        JapaneseEraRepositoryComponentModule::class,
        UserSettingsComponentModule::class,
        DateStrComponentModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: App): Context = application

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

@Module
abstract class BroadcastReceiverModule {

    @ContributesAndroidInjector
    abstract fun contributeBootCompletedReceiver(): BootCompletedReceiver

}

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class, // これが必要
        AppModule::class, ActivityModule::class, ServiceModule::class, BroadcastReceiverModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

    fun inject(dateFormatListPreference: DateFormatListPreference)
}
