package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.cache.db.mapper.PlayoneMapper
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    // TODO(jieyi): 2018/03/10 Just don't care cache layer's mapper temporally.
    @Provides
    internal fun providePlayoneMapper() = PlayoneMapper()
}