package com.nagopy.android.bashfulclock.usersettings

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UserSettingsModule::class])
interface UserSettingsComponent {

    fun userSettings(): UserSettings

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): UserSettingsComponent
    }

    companion object {
        fun builder(): Builder = DaggerUserSettingsComponent.builder()
    }
}
