package com.nagopy.android.bashfulclock.analytics

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AnalyticsModule::class])
interface AnalyticsComponent {

    fun analytics(): Analytics

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AnalyticsComponent
    }

    companion object {
        fun builder(): Builder = DaggerAnalyticsComponent.builder()
    }
}
