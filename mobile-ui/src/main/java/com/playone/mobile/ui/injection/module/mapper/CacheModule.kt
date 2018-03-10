package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.cache.db.mapper.PlayoneMapper
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    internal fun providePlayoneMapper() = PlayoneMapper()
}