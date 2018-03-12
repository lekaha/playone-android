package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.remote.mapper.EntityMapper
import com.playone.mobile.remote.mapper.PlayoneEntityMapper
import com.playone.mobile.remote.mapper.UserEntityMapper
import com.playone.mobile.remote.model.PlayoneModel
import com.playone.mobile.remote.model.UserModel
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class RemoteModule {

    @Provides
    internal fun providePlayoneEntityMapper(
        modelMapper: ModelMapper
    ): EntityMapper<PlayoneModel, PlayoneEntity> = PlayoneEntityMapper(modelMapper)

    @Provides
    internal fun providePlayoneUserEntityMapper(
        modelMapper: ModelMapper
    ): EntityMapper<UserModel, UserEntity> = UserEntityMapper(modelMapper)
}