package com.nagopy.android.bashfulclock.japaneseera

import android.content.Context
import com.nagopy.android.bashfulclock.analytics.DaggerRemoteConfigComponent
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [JapaneseEraModule::class])
interface JapaneseEraComponent {

    fun japaneseEraRepository(): JapaneseEraRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun remoteConfig(remoteConfig: RemoteConfig): Builder

        fun build(): JapaneseEraComponent
    }

    companion object {
        fun builder(): Builder = DaggerJapaneseEraComponent.builder()
    }
}
