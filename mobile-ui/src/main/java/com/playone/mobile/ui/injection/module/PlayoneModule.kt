package com.playone.mobile.ui.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.playone.mobile.cache.PlayoneCacheImpl
import com.playone.mobile.cache.PreferencesHelper
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.db.mapper.PlayoneMapper
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
import com.playone.mobile.ui.firebase.FirebaseAuthenticator
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class PlayoneModule {

    @Provides
    internal fun providePlayoneMapper() = PlayoneMapper()

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
    internal fun providePlayoneEntityMapper(modelMapper: ModelMapper) =
        PlayoneEntityMapper(modelMapper)

    @Provides
    internal fun providePlayoneUserEntityMapper(modelMapper: ModelMapper) =
        UserEntityMapper(modelMapper)

    @Provides
    internal fun providePlayoneRemote(
        service: PlayoneService,
        playoneMapper: PlayoneEntityMapper,
        userMapper: UserEntityMapper
    ): PlayoneRemote = PlayoneRemoteImpl(service, playoneMapper, userMapper)

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
    internal fun providePlayoneDataMapper(modelMapper: ModelMapper) =
        com.playone.mobile.data.mapper.PlayoneMapper(modelMapper)

    @Provides
    internal fun proideDataUserMapper(modelMapper: ModelMapper) =
        UserMapper(modelMapper)

    @Provides
    internal fun proideUserMapper(modelMapper: ModelMapper) =
        com.playone.mobile.presentation.mapper.UserMapper(modelMapper)

    @Provides
    internal fun providePlayoneRepository(
        factory: PlayoneDataStoreFactory,
        playoneMapper: com.playone.mobile.data.mapper.PlayoneMapper,
        userMapper: UserMapper
    ): PlayoneRepository = PlayoneDataRepository(factory, playoneMapper, userMapper)

    @Provides
    internal fun provideAuthenticator(firebaseAuth: FirebaseAuth)
        : Authenticator = FirebaseAuthenticator(firebaseAuth)

    @Provides
    internal fun provideSignUpAndSignIn(
        playoneRepository: PlayoneRepository,
        authenticator: Authenticator,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = SignUpAndSignIn(playoneRepository, authenticator, threadExecutor, postExecutionThread)

}