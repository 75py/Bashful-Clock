package com.nagopy.android.bashfulclock.japaneseera

import com.nagopy.android.bashfulclock.japaneseera.JapaneseEraRepository
import com.nagopy.android.bashfulclock.japaneseera.internal.JapaneseEraRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object JapaneseEraModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideJapaneseEraRepository(impl: JapaneseEraRepositoryImpl): JapaneseEraRepository = impl

}
