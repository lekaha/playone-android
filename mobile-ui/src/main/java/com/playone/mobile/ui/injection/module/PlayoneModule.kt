package com.playone.mobile.ui.injection.module

import com.playone.mobile.cache.PlayoneCacheImpl
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.db.mapper.PlayoneMapper
import com.playone.mobile.cache.mapper.BufferooEntityMapper
import com.playone.mobile.data.PlayoneDataRepository
import com.playone.mobile.data.mapper.UserMapper
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.data.source.PlayoneCacheDataStore
import com.playone.mobile.data.source.PlayoneDataStoreFactory
import com.playone.mobile.data.source.PlayoneRemoteDataStore
import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.domain.repository.PlayoneRepository
import com.playone.mobile.remote.PlayoneRemoteImpl
import com.playone.mobile.remote.bridge.playone.PlayoneService
import com.playone.mobile.remote.mapper.PlayoneEntityMapper
import com.playone.mobile.remote.mapper.UserEntityMapper
import dagger.Module
import dagger.Provides

@Module
class PlayoneModule {

    @Provides
    internal fun providePlayoneDataCache(
        factory: DbOpenHelper,
        entityMapper: BufferooEntityMapper,
        mapper: PlayoneMapper,
        helper: PreferencesHelper
    ) = PlayoneCacheImpl(factory, entityMapper, mapper, helper)

    @Provides
    internal fun providePlayoneCacheDataStore(playoneCache: PlayoneCache) =
        PlayoneCacheDataStore(playoneCache)

    @Provides
    internal fun providePlayoneEntityMapper() = PlayoneEntityMapper()

    @Provides
    internal fun providePlayoneUserEntityMapper() = UserEntityMapper()

    @Provides
    internal fun providePlayoneRemote(
        service: PlayoneService,
        playoneMapper: PlayoneEntityMapper,
        userMapper: UserEntityMapper
    ) = PlayoneRemoteImpl(service, playoneMapper, userMapper)

    @Provides
    internal fun providePlayoneRemoteDataStore(playoneRemote: PlayoneRemote) =
        PlayoneRemoteDataStore(playoneRemote)

    @Provides
    internal fun providePlayoneDataStoreFactory(
        playoneCache: PlayoneCache,
        playoneCacheDataStore: PlayoneCacheDataStore,
        playoneRemoteDataStore: PlayoneRemoteDataStore
    ) = PlayoneDataStoreFactory(playoneCache, playoneCacheDataStore, playoneRemoteDataStore)

    @Provides
    internal fun providePlayoneRepository(
        factory: PlayoneDataStoreFactory,
        playoneMapper: com.playone.mobile.data.mapper.PlayoneMapper,
        userMapper: UserMapper
    ) = PlayoneDataRepository(factory, playoneMapper, userMapper)

    @Provides
    internal fun provideSignUpAndSignIn(
        playoneRepository: PlayoneRepository,
        authenticator: Authenticator,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = SignUpAndSignIn(playoneRepository, authenticator, threadExecutor, postExecutionThread)

}