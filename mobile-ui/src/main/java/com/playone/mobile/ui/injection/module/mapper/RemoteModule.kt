package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.remote.mapper.PlayoneEntityMapper
import com.playone.mobile.remote.mapper.UserEntityMapper
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class RemoteModule {

    @Provides
    internal fun providePlayoneEntityMapper(modelMapper: ModelMapper) =
        PlayoneEntityMapper(modelMapper)

    @Provides
    internal fun providePlayoneUserEntityMapper(modelMapper: ModelMapper) =
        UserEntityMapper(modelMapper)
}