package com.playone.mobile.ui.injection.module.storage

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.data.source.PlayoneRemoteDataStore
import com.playone.mobile.remote.PlayoneRemoteImpl
import com.playone.mobile.remote.bridge.playone.PlayoneService
import com.playone.mobile.remote.mapper.EntityMapper
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import com.playone.mobile.ui.injection.module.mapper.RemoteModule
import dagger.Module
import dagger.Provides

@Module(includes = [RemoteModule::class])
class PlayoneRemoteModule {

    @Provides
    internal fun providePlayoneRemote(
        service: PlayoneService,
        playoneMapper: EntityMapper<PlayoneModel, PlayoneEntity>,
        userMapper: EntityMapper<UserModel, UserEntity>
    ): PlayoneRemote = PlayoneRemoteImpl(service, playoneMapper, userMapper)

    @Provides
    internal fun providePlayoneRemoteDataStore(playoneRemote: PlayoneRemote) =
        PlayoneRemoteDataStore(playoneRemote)
}