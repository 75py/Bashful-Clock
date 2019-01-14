package com.nagopy.android.bashfulclock.infra

import com.nagopy.android.bashfulclock.domain.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.domain.usersettings.UserSettings
import com.nagopy.android.bashfulclock.infra.japaneseera.JapaneseEraRepositoryImpl
import com.nagopy.android.bashfulclock.infra.usersettings.UserSettingsImpl
import dagger.Binds
import dagger.Module

@Module
interface InfraModule {

    @Binds
    fun provideUserSettings(userSettingsImpl: UserSettingsImpl): UserSettings

    @Binds
    fun provideJapaneseEraRepository(japaneseEraRepositoryImpl: JapaneseEraRepositoryImpl): JapaneseEraRepository

}
