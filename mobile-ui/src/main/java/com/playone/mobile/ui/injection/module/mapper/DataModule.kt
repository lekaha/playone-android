package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.data.mapper.PlayoneMapper
import com.playone.mobile.data.mapper.UserMapper
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class DataModule {

    @Provides
    internal fun proideDataUserMapper(modelMapper: ModelMapper) =
        UserMapper(modelMapper)

    @Provides
    internal fun providePlayoneDataMapper(modelMapper: ModelMapper) =
        PlayoneMapper(modelMapper)
}