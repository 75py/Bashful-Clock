package com.nagopy.android.bashfulclock.datestr

import android.content.Context
import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.usersettings.UserSettings
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DateStrModule::class])
interface DateStrComponent {

    fun dateFormatter(): DateFormatter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun userSettings(userSettings: UserSettings): Builder

        @BindsInstance
        fun japaneseEraRepository(japaneseEraRepository: JapaneseEraRepository): Builder

        fun build(): DateStrComponent
    }

    companion object {
        fun builder(): Builder = DaggerDateStrComponent.builder()
    }
}
