package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.data.mapper.Mapper
import com.playone.mobile.data.mapper.PlayoneMapper
import com.playone.mobile.data.mapper.UserMapper
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class DataModule {

    @Provides
    internal fun proideDataUserMapper(modelMapper: ModelMapper): Mapper<UserEntity, User> =
        UserMapper(modelMapper)

    @Provides
    internal fun providePlayoneDataMapper(
        modelMapper: ModelMapper
    ): Mapper<PlayoneEntity, Playone> = PlayoneMapper(modelMapper)
}