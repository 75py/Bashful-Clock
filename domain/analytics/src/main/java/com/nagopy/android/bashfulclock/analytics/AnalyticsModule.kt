package com.nagopy.android.bashfulclock.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.nagopy.android.bashfulclock.analytics.internal.AnalyticsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object AnalyticsModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    @JvmStatic
    @Singleton
    @Provides
    fun provideAnalytics(impl: AnalyticsImpl): Analytics = impl

}
