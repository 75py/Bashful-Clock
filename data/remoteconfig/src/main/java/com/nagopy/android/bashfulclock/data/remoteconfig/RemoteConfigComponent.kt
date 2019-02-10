package com.nagopy.android.bashfulclock.analytics

import android.content.Context
import com.nagopy.android.bashfulclock.data.remoteconfig.RemoteConfig
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteConfigModule::class])
interface RemoteConfigComponent {

    fun remoteConfig(): RemoteConfig

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): RemoteConfigComponent
    }

    companion object {
        fun builder(): Builder = DaggerRemoteConfigComponent.builder()
    }
}
