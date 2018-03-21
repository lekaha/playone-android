package com.playone.mobile.ui.injection.module

import com.playone.mobile.data.PlayoneDataRepository
import com.playone.mobile.data.mapper.Mapper
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.source.PlayoneCacheDataStore
import com.playone.mobile.data.source.PlayoneDataStoreFactory
import com.playone.mobile.data.source.PlayoneRemoteDataStore
import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.interactor.playone.GetPlayoneDetail
import com.playone.mobile.domain.interactor.playone.GetPlayoneList
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import com.playone.mobile.domain.repository.PlayoneRepository
import com.playone.mobile.ui.injection.module.mapper.DataModule
import com.playone.mobile.ui.injection.module.mapper.PresentationModule
import com.playone.mobile.ui.injection.module.storage.PlayoneDataCacheModule
import com.playone.mobile.ui.injection.module.storage.PlayoneRemoteModule
import dagger.Module
import dagger.Provides

@Module(includes = [
    PlayoneDataCacheModule::class,
    PlayoneRemoteModule::class,
    DataModule::class,
    PresentationModule::class
])
class PlayoneModule {

    @Provides
    internal fun providePlayoneDataStoreFactory(
        playoneCache: PlayoneCache,
        playoneCacheDataStore: PlayoneCacheDataStore,
        playoneRemoteDataStore: PlayoneRemoteDataStore
    ) = PlayoneDataStoreFactory(playoneCache, playoneCacheDataStore, playoneRemoteDataStore)

    @Provides
    internal fun providePlayoneRepository(
        factory: PlayoneDataStoreFactory,
        playoneMapper: Mapper<PlayoneEntity, Playone>,
        userMapper: Mapper<UserEntity, User>
    ): PlayoneRepository = PlayoneDataRepository(factory, playoneMapper, userMapper)

    @Provides
    internal fun provideSignUpAndSignIn(
        playoneRepository: PlayoneRepository,
        authenticator: Authenticator,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = SignUpAndSignIn(playoneRepository, authenticator, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetCurrentUser(
        authenticator: Authenticator,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetCurrentUser(authenticator, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetPlayoneList(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetPlayoneList(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetPlayoneDetail(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetPlayoneDetail(playoneRepository, threadExecutor, postExecutionThread)
}