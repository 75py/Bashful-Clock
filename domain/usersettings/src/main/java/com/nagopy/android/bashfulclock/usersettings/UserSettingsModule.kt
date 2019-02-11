package com.nagopy.android.bashfulclock.usersettings

import android.content.Context
import android.preference.PreferenceManager
import com.nagopy.android.bashfulclock.usersettings.internal.UserSettingsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object UserSettingsModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @JvmStatic
    @Singleton
    @Provides
    fun provideResources(context: Context) = context.resources

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserSettings(impl: UserSettingsImpl): UserSettings = impl

}
