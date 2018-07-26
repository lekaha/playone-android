package com.playone.mobile.ui.injection.module

import com.playone.mobile.data.CacheChecker
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
import com.playone.mobile.domain.interactor.favorite.FavoritePlayone
import com.playone.mobile.domain.interactor.playone.CreatePlayone
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.interactor.playone.GetFavotitePlayoneList
import com.playone.mobile.domain.interactor.playone.GetJoinedPlayoneList
import com.playone.mobile.domain.interactor.playone.GetOwnPlayoneList
import com.playone.mobile.domain.interactor.playone.GetPlayoneDetail
import com.playone.mobile.domain.interactor.playone.GetPlayoneList
import com.playone.mobile.domain.interactor.user.GetUser
import com.playone.mobile.domain.interactor.user.UpdateUserProfile
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
            playoneCacheChecker: CacheChecker,
            playoneCacheDataStore: PlayoneCacheDataStore,
            playoneRemoteDataStore: PlayoneRemoteDataStore
    ) = PlayoneDataStoreFactory(playoneCacheChecker, playoneCacheDataStore, playoneRemoteDataStore)

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
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetCurrentUser(authenticator, playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetPlayoneList(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetPlayoneList(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetFavoritePlayoneList(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetFavotitePlayoneList(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetJoinedPlayoneList(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetJoinedPlayoneList(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetOwnPlayoneList(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetOwnPlayoneList(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideCreatePlayone(
        signUpAndSignIn: SignUpAndSignIn,
        getCurrentUser: GetCurrentUser,
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = CreatePlayone(signUpAndSignIn,
                      getCurrentUser,
                      playoneRepository,
                      threadExecutor,
                      postExecutionThread)

    @Provides
    internal fun provideGetPlayoneDetail(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetPlayoneDetail(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideFavoritePlayone(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = FavoritePlayone(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideGetUser(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = GetUser(playoneRepository, threadExecutor, postExecutionThread)

    @Provides
    internal fun provideUpdateUserProfile(
        playoneRepository: PlayoneRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) = UpdateUserProfile(playoneRepository, threadExecutor, postExecutionThread)
}