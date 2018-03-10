package com.playone.mobile.ui.injection.module.mapper

import com.playone.mobile.presentation.mapper.UserViewMapper
import dagger.Module
import dagger.Provides
import org.modelmapper.ModelMapper

@Module
class PresentationModule {

    @Provides
    internal fun proideUserMapper(modelMapper: ModelMapper) =
        UserViewMapper(modelMapper)
}