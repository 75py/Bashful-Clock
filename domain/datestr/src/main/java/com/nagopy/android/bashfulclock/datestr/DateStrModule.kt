package com.nagopy.android.bashfulclock.datestr

import com.nagopy.android.bashfulclock.datestr.internal.DateFormatterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object DateStrModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDateFormatter(impl: DateFormatterImpl): DateFormatter = impl

}
