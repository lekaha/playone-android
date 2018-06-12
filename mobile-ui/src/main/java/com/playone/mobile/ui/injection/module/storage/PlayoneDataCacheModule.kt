package com.playone.mobile.ui.injection.module.storage

import com.playone.mobile.cache.PlayoneCacheImpl
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.db.mapper.PlayoneMapper
import com.playone.mobile.data.CacheChecker
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.source.PlayoneCacheDataStore
import com.playone.mobile.ui.injection.module.mapper.CacheModule
import dagger.Module
import dagger.Provides

@Module(includes = [CacheModule::class])
class PlayoneDataCacheModule {

    @Provides
    internal fun providePlayoneDataCache(
        factory: DbOpenHelper,
        mapper: PlayoneMapper,
        helper: PreferencesHelper
    ): PlayoneCache = PlayoneCacheImpl(factory, mapper, helper)

    @Provides
    internal fun providePlayoneCacheDataStore(playoneCache: PlayoneCache) =
        PlayoneCacheDataStore(playoneCache)

    @Provides
    internal fun providePlayoneCacheChecker(playoneCache: PlayoneCache): CacheChecker =
        playoneCache as CacheChecker
}